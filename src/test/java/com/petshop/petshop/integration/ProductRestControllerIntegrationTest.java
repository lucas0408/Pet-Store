package com.petshop.petshop.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.petshop.model.Product;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductRestControllerIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String FOO_RESOURCE_URL = "/api/products";

    @WithMockUser(username = "admin")
    @Test
    void shouldReturnListOfProducts() throws Exception {
        Product product = setUp();
        mockMvc.perform(get(FOO_RESOURCE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(product.getName()))
                .andExpect(jsonPath("$[0].unitPrice").value("79.9"))
                .andExpect(jsonPath("$[0].unitsInStock").value(product.getUnitsInStock()))
                .andExpect(jsonPath("$[0].imageUrl").value(product.getImageUrl()));
    }

    @WithMockUser(username = "admin")
    @Test
    void shouldReturnProductById() throws Exception {
        Product product = setUp();
        mockMvc.perform(get(FOO_RESOURCE_URL + "/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.unitPrice").value("79.9"))
                .andExpect(jsonPath("$.unitsInStock").value(product.getUnitsInStock()))
                .andExpect(jsonPath("$.imageUrl").value(product.getImageUrl()));
    }

    @WithMockUser(username = "admin")
    @Test
    void shouldCreateProduct() throws Exception {
        Product product = createTestProduct();
        mockMvc.perform(multipart(FOO_RESOURCE_URL)
                        .param("name", "Ração Premium")
                        .param("unitPrice", "79.90")
                        .param("unitsInStock", "50")
                        .param("imageUrl", "")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.unitPrice").value("79.9"))
                .andExpect(jsonPath("$.unitsInStock").value(product.getUnitsInStock()))
                .andExpect(jsonPath("$.imageUrl").value(product.getImageUrl()));
    }

    @WithMockUser(username = "admin")
    @Test
    void shouldUpdateProduct() throws Exception {
        Product product = setUp();
        mockMvc.perform(put(FOO_RESOURCE_URL + "/" + product.getId())
                        .param("name", "updateProduct")
                        .param("unitPrice", "89.90")
                        .param("unitsInStock", "49")
                        .param("imageUrl", "")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("updateProduct"))
                .andExpect(jsonPath("$.unitPrice").value("89.9"))
                .andExpect(jsonPath("$.unitsInStock").value(49));
    }

    @WithMockUser(username = "admin")
    @Test
    void shouldDeleteProduct() throws Exception {
        Product product = setUp();
        mockMvc.perform(delete(FOO_RESOURCE_URL + "/" + product.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get(FOO_RESOURCE_URL + "/" + product.getId()))
                .andExpect(status().isNotFound());
    }

    private Product setUp() {
        return productRepository.save(createTestProduct());
    }

    private Product createTestProduct() {
        productRepository.deleteAll();
        Product product = new Product();
        product.setName("Ração Premium");
        product.setUnitPrice(new BigDecimal("79.90"));
        product.setUnitsInStock(50);
        product.setImageUrl(null);
        return product;
    }
}
