package com.petshop.petshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.petshop.model.Category;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.model.User;
import com.petshop.petshop.repository.CategoryRepository;
import com.petshop.petshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoryRestControllerIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String FOO_RESOURCE_URL = "/api/categories";

    @WithMockUser(username = "admin")
    @Test
    void shouldReturnListOfProducts() throws Exception {
        Category category = setUp();
        mockMvc.perform(get(FOO_RESOURCE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(category.getName()));
    }

    @WithMockUser(username = "admin")
    @Test
    void shouldReturnProductByName() throws Exception {
        Category category = setUp();
        mockMvc.perform(get(FOO_RESOURCE_URL + "/" + category.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(category.getName()));
    }

    @WithMockUser(username = "admin")
    @Test
    void shouldCreateProduct() throws Exception {
        Category category = createTestCategory();
        mockMvc.perform(multipart(FOO_RESOURCE_URL)
                        .param("name", category.getName())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(category.getName()));
    }

    @WithMockUser(username = "admin")
    @Test
    void shouldUpdateCategory() throws Exception {
        Category category = setUp();
        mockMvc.perform(put(FOO_RESOURCE_URL + "/" + category.getId())
                        .param("name", "updateCategory")
                        .param("imageUrl", "")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("updateCategory"));
    }

    @WithMockUser(username = "admin")
    @Test
    void shouldDeleteCategory() throws Exception {
        Category category = setUp();
        mockMvc.perform(delete(FOO_RESOURCE_URL + "/" + category.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get(FOO_RESOURCE_URL + "/" + category.getId()))
                .andExpect(status().isNotFound());
    }

    private Category setUp() {
        return categoryRepository.save(createTestCategory());
    }

    private Category createTestCategory() {
        categoryRepository.deleteAll();
        Category category = new Category();
        category.setName("TesteCategory");
        return category;
    }
}
