package com.petshop.petshop.service;

import com.petshop.petshop.DTO.ApiResponseDTO;
import com.petshop.petshop.model.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public abstract ApiResponseDTO<List<Category>> getAllCategory();
    public abstract ApiResponseDTO<Category> getCategory(String id);
    public abstract ApiResponseDTO<Category> createCategory(Category category, MultipartFile image);
    public abstract ApiResponseDTO<Category> updateCategory(String id, Category updateCategory, MultipartFile image);
    public abstract void deleteCategory(String id);
}
