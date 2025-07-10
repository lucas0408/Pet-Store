package com.petshop.petshop.service;

import com.petshop.petshop.DTO.ApiResponseDTO;
import com.petshop.petshop.DTO.UserDTO;
import com.petshop.petshop.DTO.UserResponseDTO;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public abstract List<UserResponseDTO> getAllUsers();
    public abstract UserResponseDTO getUser(String id);
    public abstract UserResponseDTO createUser(UserDTO requestNewUser);
    public abstract UserResponseDTO updateUser(String id, UserDTO updateUser);
    public abstract void deleteUser(String id);
}
