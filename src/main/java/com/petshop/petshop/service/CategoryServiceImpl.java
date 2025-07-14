package com.petshop.petshop.service;

import com.petshop.petshop.DTO.CategoryDTO;
import com.petshop.petshop.DTO.CategoryResponseDTO;
import com.petshop.petshop.exception.ResourceNotFoundException;
import com.petshop.petshop.model.Category;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.repository.CategoryRepository;
import com.petshop.petshop.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final ImageService imageService;

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    public CategoryServiceImpl(ImageService imageService,
                               CategoryRepository categoryRepository,
                               ProductRepository productRepository){
        this.imageService = imageService;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getAllCategory() {
        return categoryRepository.findAll().stream().map(CategoryResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO getCategory(String name) {
        return new CategoryResponseDTO(categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with name: " + name)));
    }

    @Override
    @Transactional
    public CategoryResponseDTO createCategory(CategoryDTO requestNewCategory) {
        if(categoryRepository.existsByName(requestNewCategory.name())){
            throw new ValidationException("A category with the same name already exists: " + requestNewCategory.name());
        }
        String imageUrl = imageService.saveImageToServer(requestNewCategory.image());

        Category newCategory = new Category(requestNewCategory);

        if (imageUrl != null) {
            newCategory.setImageUrl(imageUrl);
        }
        return new CategoryResponseDTO(categoryRepository.save(newCategory));
    }

    @Override
    @Transactional
    public CategoryResponseDTO updateCategory(String id, CategoryDTO requestUpdateCategory) {
        if(categoryRepository.existsByName(requestUpdateCategory.name())){
            throw new ValidationException("A category with the same name already exists: " + requestUpdateCategory.name());
        }
        return categoryRepository.findById(id).map(category -> {
            category.setName(requestUpdateCategory.name());
            if(requestUpdateCategory.image() != null && !requestUpdateCategory.image().isEmpty()) {
                imageService.deleteImageFromServer(category.getImageUrl());
                category.setImageUrl(imageService.saveImageToServer(requestUpdateCategory.image()));
            }
            if (requestUpdateCategory.imageUrl().isEmpty()){
                imageService.deleteImageFromServer(category.getImageUrl());
                category.setImageUrl(null);
            }
            return new CategoryResponseDTO(categoryRepository.save(category));
        }).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Override
    @Transactional
    public void deleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        List<Product> products = productRepository.findByCategoriesContaining(category);

        for (Product product : products) {
            product.getCategories().remove(category);
            productRepository.save(product);
        }
        imageService.deleteImageFromServer(category.getImageUrl());

        categoryRepository.delete(category);
    }
}
