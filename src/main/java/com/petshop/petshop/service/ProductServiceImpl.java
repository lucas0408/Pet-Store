package com.petshop.petshop.service;

import com.petshop.petshop.DTO.ProductRequestDTO;
import com.petshop.petshop.model.Category;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.repository.CategoryRepository;
import com.petshop.petshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProduct(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Product createProduct(ProductRequestDTO requestNewProduct) {
        Product newProduct = new Product();
        newProduct.setName(requestNewProduct.getName());
        newProduct.setUnitPrice(requestNewProduct.getUnitPrice());
        newProduct.setUnitsInStock(requestNewProduct.getUnitsInStock());
        newProduct.setCategories(getCategoriesProduct(requestNewProduct.getCategoryIds()));
        return productRepository.save(newProduct);
    }

    @Override
    public Optional<Product> updateProduct(String id, ProductRequestDTO requestUpdateProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(requestUpdateProduct.getName());
                    product.setCategories(getCategoriesProduct(requestUpdateProduct.getCategoryIds()));
                    product.setUnitPrice(requestUpdateProduct.getUnitPrice());
                    product.setUnitsInStock(requestUpdateProduct.getUnitsInStock());
                    return productRepository.save(product);
                });
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    private Set<Category> getCategoriesProduct(List<String> categoryIds){
        return categoryIds
                .stream()
                .map(categoryId ->
                        categoryRepository.findById(categoryId).orElseThrow(() ->
                                new RuntimeException("Category not found with ID: " + categoryId)))
                .collect(Collectors.toSet());
    }
}
