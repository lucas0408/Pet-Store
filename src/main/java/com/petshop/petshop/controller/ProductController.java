package com.petshop.petshop.controller;

import com.petshop.petshop.DTO.ApiResponseDTO;
import com.petshop.petshop.DTO.ProductRequestDTO;
import com.petshop.petshop.exception.ApiResponseBuilder;
import com.petshop.petshop.exception.ResourceNotFoundException;
import com.petshop.petshop.exception.ResponseMessageProvider;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.petshop.petshop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ApiResponseBuilder<List<Product>> listResponseBuilder;
    private final ApiResponseBuilder<Product> responseBuilder;
    private final ResponseMessageProvider messageProvider;

    @Autowired
    public ProductController(
            ProductService productService,
            ApiResponseBuilder<Product> responseBuilder,
            ApiResponseBuilder<List<Product>> listResponseBuilder,
            ResponseMessageProvider messageProvider
    ) {
        this.productService = productService;
        this.responseBuilder = responseBuilder;
        this.listResponseBuilder = listResponseBuilder;
        this.messageProvider = messageProvider;
    }

    @GetMapping()
    public ResponseEntity<ApiResponseDTO<List<Product>>> getAll(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(listResponseBuilder.createSuccessResponse(
                products,
                messageProvider.getSuccessListMessage()
        ));
    }

    @PostMapping()
    public ResponseEntity<ApiResponseDTO<Product>> newProduct(@Valid @RequestBody ProductRequestDTO newProduct){
        Product created = productService.createProduct(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseBuilder.createSuccessResponse(
                        created,
                        messageProvider.getSuccessCreateMessage()
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Product>> getById(@Valid @PathVariable String id){
        Product product = productService.getProduct(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return ResponseEntity.ok(responseBuilder.createSuccessResponse(
                product,
                messageProvider.getSuccessRetrieveMessage()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Product>> replaceProduct(@Valid @RequestBody ProductRequestDTO updateProduct, @PathVariable String id) {
        Product updated = productService.updateProduct(id, updateProduct)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return ResponseEntity.ok(responseBuilder.createSuccessResponse(
                updated,
                messageProvider.getSuccessUpdateMessage()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(responseBuilder.createSuccessResponse(
                null,
                messageProvider.getSuccessDeleteMessage()
        ));
    }
}
