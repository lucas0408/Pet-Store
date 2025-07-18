package com.petshop.petshop.controller;

import com.petshop.petshop.DTO.ProductDTO;
import com.petshop.petshop.DTO.ProductResponseDTO;
import com.petshop.petshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> getAll(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping()
    public ResponseEntity<ProductResponseDTO> newProduct(
            @ModelAttribute @Valid ProductDTO productData) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(productData));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@Valid @PathVariable String id){
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> replaceProduct(@PathVariable String id,
                                                             @ModelAttribute @Valid ProductDTO updateProduct){

        return ResponseEntity.ok(productService.updateProduct(id, updateProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
