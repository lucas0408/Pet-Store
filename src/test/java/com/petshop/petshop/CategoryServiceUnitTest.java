package com.petshop.petshop;

import com.petshop.petshop.DTO.CategoryDTO;
import com.petshop.petshop.DTO.CategoryResponseDTO;
import com.petshop.petshop.DTO.ProductDTO;
import com.petshop.petshop.DTO.ProductResponseDTO;
import com.petshop.petshop.exception.ResourceNotFoundException;
import com.petshop.petshop.model.Category;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.repository.CategoryRepository;
import com.petshop.petshop.repository.ProductRepository;
import com.petshop.petshop.service.CategoryServiceImpl;
import com.petshop.petshop.service.ImageService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceUnitTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Should get all categories")
    public void getAllCategories(){
        List<Category> categories = new ArrayList<>();
        Category category = createCategory();

        categories.add(category);

        List<CategoryResponseDTO> categoryResponseDTOS = new ArrayList<>();
        categoryResponseDTOS.add(new CategoryResponseDTO(category));

        given(categoryRepository.findAll()).willReturn(categories);

        List<CategoryResponseDTO> getAllCategories = categoryService.getAllCategory();

        assertEquals(getAllCategories, categoryResponseDTOS);
    }

    @Test
    @DisplayName("Should get category by id")
    public void getCategoryByIdHappyFlow(){

        Category category = createCategory();

        given(categoryRepository.findByName(category.getName())).willReturn(Optional.of(category));

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(category);

        CategoryResponseDTO getCategoryDTO = categoryService.getCategory(category.getName());

        assertEquals(getCategoryDTO, categoryResponseDTO);
    }

    @Test
    @DisplayName("Should not get category by name")
    public void NotGetCategoryByName(){

        given(categoryRepository.findByName("any")).willReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.getCategory("any")
        );
    }

    @Test
    @DisplayName("Should create a category")
    public void CreateCategoryByHappyFlow(){

        CategoryDTO categoryDTO = createCategoryDTO();

        Category category = createCategory();

        given(categoryRepository.existsByName(categoryDTO.name())).willReturn(false);
        given(categoryRepository.save(any(Category.class))).willReturn(category);

        CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryDTO);

        assertEquals(categoryResponseDTO, new CategoryResponseDTO(category));
    }

    @Test
    @DisplayName("Category with exist name in DB")
    public void CreateCategoryWithExistName(){

        CategoryDTO categoryDTO = createCategoryDTO();

        given(categoryRepository.existsByName(categoryDTO.name())).willReturn(true);

        assertThrows(
                ValidationException.class,
                () -> categoryService.createCategory(categoryDTO)
        );

        verify(categoryRepository, never())
                .save(any(Category.class));
    }

    @Test
    @DisplayName("Should update a category")
    public void UpdateCategoryByHappyFlow(){

        CategoryDTO updateCategoryDTO = createCategoryDTO();

        Category category = createCategory();

        given(categoryRepository.existsByName(updateCategoryDTO.name())).willReturn(false);
        given(categoryRepository.findById(category.getId())).willReturn(Optional.of(category));
        given(categoryRepository.save(any(Category.class))).willReturn(category);

        CategoryResponseDTO categoryResponseDTO = categoryService.updateCategory(category.getId(), updateCategoryDTO);

        assertEquals(categoryResponseDTO, new CategoryResponseDTO(category));
    }

    @Test
    @DisplayName("Should not update a category with invalid ID")
    public void UpdateWithInvalidId(){

        CategoryDTO updateCategoryDTO = createCategoryDTO();

        Category category = createCategory();

        given(categoryRepository.existsByName(updateCategoryDTO.name())).willReturn(false);
        given(categoryRepository.findById(category.getId())).willReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.updateCategory(category.getId(), updateCategoryDTO)
        );

        verify(categoryRepository, never())
                .save(any(Category.class));
    }

    @Test
    @DisplayName("Should delete a Category")
    public void DeleteCategoryWithValidID(){

        Category category = createCategory();

        Product product = new Product();
        List<Product> products = List.of(product);

        given(categoryRepository.findById(category.getId())).willReturn(Optional.of(category));
        given(productRepository.findByCategoriesContaining(category))
                .willReturn(products);


        categoryService.deleteCategory(category.getId());

        verify(categoryRepository)
                .delete(category);
    }

    @Test
    @DisplayName("Should not delete a Category")
    public void DeleteCategoryWithInvalidID(){

        Category category = createCategory();

        given(categoryRepository.findById(category.getId())).willReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.deleteCategory(category.getId())
        );

        verify(categoryRepository, never())
                .deleteById(category.getId());
    }

    public Category createCategory() {
        Category category = new Category();
        category.setId(UUID.randomUUID().toString());
        category.setName("Alimentos");
        return category;
    }

    public CategoryDTO createCategoryDTO(){
        return new CategoryDTO(
                null,
                "Pássaro",
                "https://example.com/passaro.jpg",
                null
        );
    }

}
