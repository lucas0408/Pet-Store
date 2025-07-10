package com.petshop.petshop.service;

import com.petshop.petshop.DTO.CategoryDTO;
import com.petshop.petshop.DTO.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    public abstract List<CategoryResponseDTO> getAllCategory();
    public abstract CategoryResponseDTO getCategory(String id);
    public abstract CategoryResponseDTO createCategory(CategoryDTO category);
    public abstract CategoryResponseDTO updateCategory(String id, CategoryDTO updateCategory);
    public abstract void deleteCategory(String id);
}
