package com.petshop.petshop.service;

import com.petshop.petshop.DTO.UserDTO;
import com.petshop.petshop.DTO.UserResponseDTO;

import java.util.List;

public interface UserService {
    public abstract List<UserResponseDTO> getAllUsers();
    public abstract UserResponseDTO getUser(String id);
    public abstract UserResponseDTO createUser(UserDTO requestNewUser);
    public abstract UserResponseDTO updateUser(String id, UserDTO updateUser);
    public abstract void deleteUser(String id);
}
