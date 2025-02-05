package com.petshop.petshop.repository;

import com.petshop.petshop.model.Category;
import com.petshop.petshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsByName(String name);
    List<Product> findByCategoriesContaining(Category category);
}
