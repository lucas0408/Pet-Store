package com.petshop.petshop.service;

import com.petshop.petshop.model.Product;
import com.petshop.petshop.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public abstract List<User> getAllUsers();
    public abstract Optional<User> getUser(String id);
    public abstract User createUser(User requestNewUser);
    public abstract Optional<User> updateUser(String id, User updateUser);
    public abstract void deleteUser(String id);
}
