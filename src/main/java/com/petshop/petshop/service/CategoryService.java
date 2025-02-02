package com.petshop.petshop.service;

import com.petshop.petshop.DTO.ApiResponseDTO;
import com.petshop.petshop.DTO.CategoryDTO;
import com.petshop.petshop.model.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public abstract ApiResponseDTO<List<Category>> getAllCategory();
    public abstract ApiResponseDTO<Category> getCategory(String id);
    public abstract ApiResponseDTO<Category> createCategory(CategoryDTO category);
    public abstract ApiResponseDTO<Category> updateCategory(String id, CategoryDTO updateCategory);
    public abstract void deleteCategory(String id);
}
