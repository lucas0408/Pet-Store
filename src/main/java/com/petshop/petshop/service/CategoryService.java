package com.petshop.petshop.service;

import com.petshop.petshop.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public abstract List<Category> getAllCategory();
    public abstract Optional<Category> getCategory(String id);
    public abstract Category createCategory(Category category);
    public abstract Optional<Category> updateCategory(String id, Category updateCategory);
    public abstract void deleteCategory(String id);
}
