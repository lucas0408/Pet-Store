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
import java.util.Set;
import java.util.stream.Collectors;

public interface ProductService {
    public abstract List<ProductResponseDTO> getAllProducts();
    public abstract ProductResponseDTO getProduct(String id);
    public abstract ProductResponseDTO createProduct(ProductDTO requestNewProduct);
    public abstract ProductResponseDTO updateProduct(String id, ProductDTO requestUpdateProduct);
    public abstract void deleteProduct(String id);


}
