package com.petshop.petshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.petshop.DTO.ApiResponseDTO;
import com.petshop.petshop.DTO.CategoryDTO;
import com.petshop.petshop.model.Category;
import com.petshop.petshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "https://pet-shop-front-end-3kzv.vercel.app")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<ApiResponseDTO<List<Category>>> getAll(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @PostMapping()
    public ResponseEntity<ApiResponseDTO<Category>> newCategory(
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("categoryData") String categoryDataJson) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        CategoryDTO newCategory = mapper.readValue(categoryDataJson, CategoryDTO.class);
        newCategory.setImage(image);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(newCategory));
    }

    @GetMapping("/{name}")
    public ResponseEntity<ApiResponseDTO<Category>> findByName(@PathVariable String name){
        return ResponseEntity.ok().body(categoryService.getCategory(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Category>> replaceCategory(
            @PathVariable String id,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("categoryData") String categoryDataJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CategoryDTO updateCategory = mapper.readValue(categoryDataJson, CategoryDTO.class);
        updateCategory.setImage(image);

        return ResponseEntity.ok(categoryService.updateCategory(id, updateCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

}
