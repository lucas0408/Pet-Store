package com.petshop.petshop.service;

import com.petshop.petshop.DTO.ApiResponseDTO;
import com.petshop.petshop.DTO.ProductDTO;
import com.petshop.petshop.exception.ResourceNotFoundException;
import com.petshop.petshop.model.Category;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.repository.CategoryRepository;
import com.petshop.petshop.repository.ProductRepository;
import com.petshop.petshop.response.ApiResponseBuilder;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ApiResponseBuilder<List<Product>> listResponseBuilder;

    @Autowired
    private ApiResponseBuilder<Product> responseBuilder;

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDTO<List<Product>> getAllProducts() {
        return listResponseBuilder.createSuccessResponse(productRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDTO<Product> getProduct(String id) {
        return  responseBuilder.createSuccessResponse(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id)));
    }

    @Override
    @Transactional
    public ApiResponseDTO<Product> createProduct(ProductDTO requestNewProduct) {

        if (productRepository.existsByName(
                requestNewProduct.getName().trim().replace("\\s+", ""))) {
            throw new ValidationException("Já existe um produto de mesmo nome cadastrado no sistema");
        }

        Product newProduct = new Product(requestNewProduct);

        Set<Category> managedCategories = new HashSet<>();
        for (Category category : newProduct.getCategories()) {
            Category managedCategory = categoryRepository.findById(category.getId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + category.getId()));
            managedCategories.add(managedCategory);
        }

        newProduct.setCategories(managedCategories);

        String imageUrl = imageService.saveImageToServer(requestNewProduct.getImage());
        if (imageUrl != null) {
            newProduct.setImageUrl(imageUrl);
        }

        System.out.println(newProduct);

        Product product = productRepository.save(newProduct);

        System.out.println(product);

        return responseBuilder.createSuccessResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public ApiResponseDTO<Product> updateProduct(String id,
                                                 ProductDTO requestUpdateProduct) {
        return responseBuilder.createSuccessResponse(productRepository.findById(id)
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

                    return productRepository.save(product);
                }).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id))
        );

    }

    @Override
    @Transactional
    public void deleteProduct(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            imageService.deleteImageFromServer(product.get().getImageUrl());
            productRepository.deleteById(id);
        }
    }

}
