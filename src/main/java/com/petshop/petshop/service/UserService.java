package com.petshop.petshop.service;

import com.petshop.petshop.DTO.ApiResponseDTO;
import com.petshop.petshop.DTO.UserDTO;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public abstract ApiResponseDTO<List<User>> getAllUsers();
    public abstract ApiResponseDTO<User> getUser(String id);
    public abstract ApiResponseDTO<User> createUser(UserDTO requestNewUser);
    public abstract ApiResponseDTO<User> updateUser(String id, User updateUser);
    public abstract void deleteUser(String id);
}
