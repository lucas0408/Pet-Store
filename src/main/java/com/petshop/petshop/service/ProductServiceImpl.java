package com.petshop.petshop.service;

import com.petshop.petshop.DTO.ProductDTO;
import com.petshop.petshop.DTO.ProductResponseDTO;
import com.petshop.petshop.exception.ResourceNotFoundException;
import com.petshop.petshop.model.Category;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.repository.CategoryRepository;
import com.petshop.petshop.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ImageService imageService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageService = imageService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream().map(ProductResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProduct(String id) {
        return  new ProductResponseDTO(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id)));
    }

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductDTO requestNewProduct) {

        if (productRepository.existsByName(
                requestNewProduct.getName().trim().replace("\\s+", ""))) {
            throw new ValidationException("Já existe um produto de mesmo nome cadastrado no sistema");
        }

        Product newProduct = new Product(requestNewProduct);

        if(!newProduct.getCategories().isEmpty() && !newProduct.getCategories().contains(null)){

            Set<Category> managedCategories = new HashSet<>();
            for (Category category : newProduct.getCategories()) {

                Category managedCategory = categoryRepository.findById(category.getId())
                        .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + category.getId()));
                managedCategories.add(managedCategory);

                newProduct.setCategories(managedCategories);
            }
        }
        
        String imageUrl = imageService.saveImageToServer(requestNewProduct.getImage());
        newProduct.setImageUrl(imageUrl);

        return new ProductResponseDTO(productRepository.save(newProduct));
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(String id, ProductDTO requestUpdateProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(requestUpdateProduct.getName());
                    product.setCategories(requestUpdateProduct.getCategories());
                    product.setUnitPrice(requestUpdateProduct.getUnitPrice());
                    product.setUnitsInStock(requestUpdateProduct.getUnitsInStock());
                    if (requestUpdateProduct.getImage() != null && !requestUpdateProduct.getImage().isEmpty()) {
                        imageService.deleteImageFromServer(product.getImageUrl());
                        product.setImageUrl(imageService.saveImageToServer(requestUpdateProduct.getImage()));
                    }
                    if (requestUpdateProduct.getImageUrl().isEmpty()){
                        imageService.deleteImageFromServer(product.getImageUrl());
                        product.setImageUrl(null);
                    }

                    return new ProductResponseDTO(productRepository.save(product));
                }).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id)
                );

    }

    @Override
    @Transactional
    public void deleteProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        imageService.deleteImageFromServer(product.getImageUrl());
        productRepository.deleteById(id);
    }

}
