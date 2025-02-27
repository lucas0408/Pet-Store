package com.petshop.petshop.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.petshop.DTO.ApiResponseDTO;
import com.petshop.petshop.DTO.ProductDTO;
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
@CrossOrigin(origins = "https://pet-shop-front-end-nu51.vercel.app")
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



        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);

        ProductDTO newProduct = mapper.readValue(productDataJson, ProductDTO.class);
        newProduct.setImage(image);

        ApiResponseDTO<Product> Product = productService.createProduct(newProduct);

        System.out.println(Product);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Product);
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

        mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);

        ProductDTO updateProduct = mapper.readValue(productDataJson, ProductDTO.class);
        updateProduct.setImage(image);


        System.out.println(productDataJson);

        return ResponseEntity.ok(productService.updateProduct(id, updateProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
