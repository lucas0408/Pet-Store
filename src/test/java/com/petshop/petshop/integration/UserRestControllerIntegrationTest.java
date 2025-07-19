package com.petshop.petshop.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.petshop.model.User;
import com.petshop.petshop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final String FOO_RESOURCE_URL = "/api/users";

    @WithMockUser(username = "admin")
    @Test
    void shouldReturnListOfUsers() throws Exception {
        User user = setUp();
        mockMvc.perform(get(FOO_RESOURCE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(user.getName()))
                .andExpect(jsonPath("$[0].login").value(user.getLogin()))
                .andExpect(jsonPath("$[0].role").value(user.getRole()));
    }

    @WithMockUser(username = "admin")
    @Test
    void shouldCreateUser() throws Exception {
        User user = createTestUser();
        mockMvc.perform(post(FOO_RESOURCE_URL)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("recrutador"))
                .andExpect(jsonPath("$.login").value("recrutador@recrutador.com"))
                .andExpect(jsonPath("$.role").value("admin"));
    }

    @WithMockUser(username = "admin")
    @Test
    void shouldUpdateUser() throws Exception {
        User user = setUp();
        user.setLogin("updateUser@loogin.com");
        user.setName("updateUser");
        user.setRole("user");
        mockMvc.perform(put(FOO_RESOURCE_URL + "/" + user.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.login").value(user.getLogin()))
                .andExpect(jsonPath("$.role").value(user.getRole()));
    }

    @WithMockUser(username = "admin")
    @Test
    void shouldDeleteUser() throws Exception {
        User user = setUp();
        mockMvc.perform(delete(FOO_RESOURCE_URL + "/" + user.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get(FOO_RESOURCE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    private User setUp() {
        userRepository.deleteAll();
        return userRepository.save(createTestUser());
    }

    private User createTestUser() {
        User user = new User();
        user.setId("73501379-5bff-4e6e-b2b4-efb0828b0da0");
        user.setLogin("recrutador@recrutador.com");
        user.setPassword("$2a$10$JS9AOnJmxVVTPAEaudPxYuby9Q6u8KvJNEm/5/HE/kVTre7SW2jzm");
        user.setRole("admin");
        user.setName("recrutador");
        return user;
    }


}
