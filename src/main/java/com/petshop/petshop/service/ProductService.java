package com.petshop.petshop.service;

import com.petshop.petshop.DTO.ProductRequestDTO;
import com.petshop.petshop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public abstract List<Product> getAllProducts();
    public abstract Optional<Product> getProduct(String id);
    public abstract Product createProduct(ProductRequestDTO product);
    public abstract Optional<Product> updateProduct(String id, ProductRequestDTO updateProduct);
    public abstract void deleteProduct(String id);
}
