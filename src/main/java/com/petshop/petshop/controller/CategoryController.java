package com.petshop.petshop.controller;

import com.petshop.petshop.DTO.CategoryDTO;
import com.petshop.petshop.DTO.CategoryResponseDTO;
import com.petshop.petshop.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<CategoryResponseDTO>> getAll(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @PostMapping()
    public ResponseEntity<CategoryResponseDTO> newCategory(@ModelAttribute @Valid CategoryDTO newCategory) {

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(newCategory));
    }

    @GetMapping("/{name}")
    public ResponseEntity<CategoryResponseDTO> findByName(@PathVariable String name){
        return ResponseEntity.ok().body(categoryService.getCategory(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> replaceCategory(
            @PathVariable String id,
            @ModelAttribute @Valid CategoryDTO updateCategory) {

        return ResponseEntity.ok(categoryService.updateCategory(id, updateCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

}