package com.petshop.petshop.DTO;


import com.petshop.petshop.model.User;

public record UserResponseDTO(String id, String name, String password, String role, String login) {
    public UserResponseDTO(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getRole(),
                user.getLogin()
        );
    }
}
