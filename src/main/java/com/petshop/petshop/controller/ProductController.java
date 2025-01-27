package com.petshop.petshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.petshop.DTO.ApiResponseDTO;
import com.petshop.petshop.response.ApiResponseBuilder;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ApiResponseBuilder<List<Product>> listResponseBuilder;

    @Autowired
    private ApiResponseBuilder<Product> responseBuilder;


    @GetMapping()
    public ResponseEntity<ApiResponseDTO<List<Product>>> getAll(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponseDTO<Product>> newProduct(
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("productData") String productDataJson) throws IOException {

        System.out.println(productDataJson);
        ObjectMapper mapper = new ObjectMapper();
        Product newProduct = mapper.readValue(productDataJson, Product.class);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(newProduct, image));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Product>> getById(@Valid @PathVariable String id){
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Product>> replaceProduct(
            @PathVariable String id,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("productData") String productDataJson) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Product updateProduct = mapper.readValue(productDataJson, Product.class);

        return ResponseEntity.ok(productService.updateProduct(id, updateProduct, image));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
