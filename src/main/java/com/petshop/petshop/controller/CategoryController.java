package com.petshop.petshop.controller;

import com.petshop.petshop.model.Category;
import com.petshop.petshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.petshop.petshop.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<Category>> all(){
        return ResponseEntity.ok().body(categoryService.getAllCategory());
    }

    @PostMapping()
    public ResponseEntity<Category> newCategory(@RequestBody Category newCategory){

        return ResponseEntity.ok().body(categoryService.createCategory(newCategory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Category>> findById(@PathVariable String id){
        return ResponseEntity.ok().body(categoryService.getCategory(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Category>> replaceCategory(@RequestBody Category updateCategory, @PathVariable String id) {
        return ResponseEntity.ok().body(categoryService.updateCategory(id, updateCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

}
