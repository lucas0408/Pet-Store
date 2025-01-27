package com.petshop.petshop.service;

import com.petshop.petshop.DTO.ApiResponseDTO;
import com.petshop.petshop.exception.ResourceNotFoundException;
import com.petshop.petshop.model.User;
import com.petshop.petshop.repository.UserRepository;
import com.petshop.petshop.response.ApiResponseBuilder;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsererviceImpl implements UserService{

    @Autowired
    private ApiResponseBuilder<List<User>> listResponseBuilder;

    @Autowired
    private ApiResponseBuilder<User> responseBuilder;

    @Autowired
    UserRepository userRepository;


    @Override
    public ApiResponseDTO<List<User>> getAllUsers() {
        return listResponseBuilder.createSuccessResponse(this.userRepository.findAll());
    }

    @Override
    public ApiResponseDTO<User> getUser(String id) {
        return responseBuilder.createSuccessResponse(this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id)));
    }

    @Override
    public ApiResponseDTO<User> createUser(User requestNewUser) {
        if(this.userRepository.findByLogin(requestNewUser.getLogin()) != null) {
            throw new ValidationException("Email já cadastrado");
        };

        String encryptedPassword = new BCryptPasswordEncoder().encode(requestNewUser.getPassword());

        User newUser = new User(requestNewUser.getLogin(), encryptedPassword, requestNewUser.getRole());

        return responseBuilder.createSuccessResponse(this.userRepository.save(newUser));
    }

    @Override
    public ApiResponseDTO<User> updateUser(String id, User updateUser) {
        return responseBuilder.createSuccessResponse(this.userRepository.findById(id).map(user ->{
                user.setLogin(updateUser.getLogin());
                user.setPassword(new BCryptPasswordEncoder().encode(updateUser.getPassword()));
                user.setRole(updateUser.getRole());
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id)));
    }

    @Override
    public void deleteUser(String id) {
        this.userRepository.deleteById(id);
    }
}
