package com.petshop.petshop.service;

import com.petshop.petshop.DTO.ApiResponseDTO;
import com.petshop.petshop.DTO.UserDTO;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public abstract List<User> getAllUsers();
    public abstract User getUser(String id);
    public abstract User createUser(UserDTO requestNewUser);
    public abstract User updateUser(String id, UserDTO updateUser);
    public abstract void deleteUser(String id);
}
