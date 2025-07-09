package com.petshop.petshop.service;

import com.petshop.petshop.DTO.ApiResponseDTO;
import com.petshop.petshop.DTO.ProductDTO;
import com.petshop.petshop.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public abstract List<Product> getAllProducts();
    public abstract Product getProduct(String id);
    public abstract Product createProduct(ProductDTO requestNewProduct);
    public abstract Product updateProduct(String id, ProductDTO requestUpdateProduct);
    public abstract void deleteProduct(String id);
}
