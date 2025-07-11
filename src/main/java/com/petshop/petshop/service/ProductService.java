package com.petshop.petshop.service;

import com.petshop.petshop.DTO.ProductDTO;
import com.petshop.petshop.DTO.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    public abstract List<ProductResponseDTO> getAllProducts();
    public abstract ProductResponseDTO getProduct(String id);
    public abstract ProductResponseDTO createProduct(ProductDTO requestNewProduct);
    public abstract ProductResponseDTO updateProduct(String id, ProductDTO requestUpdateProduct);
    public abstract void deleteProduct(String id);


}
