package com.petshop.petshop.service;

import com.petshop.petshop.DTO.ApiResponseDTO;
import com.petshop.petshop.DTO.CategoryDTO;
import com.petshop.petshop.exception.ResourceNotFoundException;
import com.petshop.petshop.model.Category;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.repository.CategoryRepository;
import com.petshop.petshop.repository.ProductRepository;
import com.petshop.petshop.response.ApiResponseBuilder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private ImageService imageService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApiResponseBuilder<List<Category>> listResponseBuilder;

    @Autowired
    private ApiResponseBuilder<Category> responseBuilder;

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDTO<List<Category>> getAllCategory() {
        return listResponseBuilder.createSuccessResponse(categoryRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDTO<Category> getCategory(String name) {
        return responseBuilder.createSuccessResponse(categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with name: " + name)));
    }

    @Override
    @Transactional
    public ApiResponseDTO<Category> createCategory(CategoryDTO requestNewCategory) {
        if(categoryRepository.existsByName(requestNewCategory.getName())){
            throw new ValidationException("A category with the same name already exists: " + requestNewCategory.getName());
        }
        String imageUrl = imageService.saveImageToServer(requestNewCategory.getImage());

        Category newCategory = new Category(requestNewCategory);

        if (imageUrl != null) {
            newCategory.setImageUrl(imageUrl);
        }
        return responseBuilder.createSuccessResponse(categoryRepository.save(newCategory));
    }

    @Override
    @Transactional
    public ApiResponseDTO<Category> updateCategory(String id, CategoryDTO requestUpdateCategory) {
        return responseBuilder.createSuccessResponse(categoryRepository.findById(id).map(category -> {
            category.setName(requestUpdateCategory.getName());
            if(requestUpdateCategory.getImage() != null && !requestUpdateCategory.getImage().isEmpty()) {
                imageService.deleteImageFromServer(category.getImageUrl());
                category.setImageUrl(imageService.saveImageToServer(requestUpdateCategory.getImage()));
            }
            if (requestUpdateCategory.getImageUrl().isEmpty()){
                imageService.deleteImageFromServer(requestUpdateCategory.getImageUrl());
                category.setImageUrl(null);
            }
            return categoryRepository.save(category);
        }).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id)));
    }

    @Override
    @Transactional
    public void deleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        // Busca todos os produtos que têm esta categoria
        List<Product> products = productRepository.findByCategoriesContaining(category);

        // Remove a categoria de cada produto
        for (Product product : products) {
            product.getCategories().remove(category);
            productRepository.save(product);
        }

        // Agora pode deletar a categoria com segurança
        categoryRepository.delete(category);
    }
}
