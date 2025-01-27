package com.petshop.petshop.service;

import com.petshop.petshop.DTO.ApiResponseDTO;
import com.petshop.petshop.exception.ResourceNotFoundException;
import com.petshop.petshop.model.Category;
import com.petshop.petshop.repository.CategoryRepository;
import com.petshop.petshop.response.ApiResponseBuilder;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private ImageService imageService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ApiResponseBuilder<List<Category>> listResponseBuilder;

    @Autowired
    private ApiResponseBuilder<Category> responseBuilder;

    @Override
    public ApiResponseDTO<List<Category>> getAllCategory() {
        return listResponseBuilder.createSuccessResponse(categoryRepository.findAll());
    }

    @Override
    public ApiResponseDTO<Category> getCategory(String name) {
        return responseBuilder.createSuccessResponse(categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with name: " + name)));
    }

    @Override
    public ApiResponseDTO<Category> createCategory(Category category, MultipartFile image) {
        if(categoryRepository.existsByName(category.getName())){
            throw new ValidationException("A category with the same name already exists: " + category.getName());
        }
        String imageUrl = imageService.saveImageToServer(image);
        if (imageUrl != null) {
            category.setImageUrl(imageUrl);
        }
        return responseBuilder.createSuccessResponse(categoryRepository.save(category));
    }

    @Override
    public ApiResponseDTO<Category> updateCategory(String id, Category updateCategory, MultipartFile image) {
        return responseBuilder.createSuccessResponse(categoryRepository.findById(id).map(category -> {
            category.setName(updateCategory.getName());
            if(image != null && !image.isEmpty()) {
                imageService.deleteImageFromServer(category.getImageUrl());
                category.setImageUrl(imageService.saveImageToServer(image));
            }
            if (updateCategory.getImageUrl().isEmpty()){
                imageService.deleteImageFromServer(updateCategory.getImageUrl());
                category.setImageUrl(null);
            }
            return categoryRepository.save(category);
        }).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id)));
    }

    @Override
    public void deleteCategory(String id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            imageService.deleteImageFromServer(category.get().getImageUrl());
            categoryRepository.deleteById(id);
        }
    }
}
