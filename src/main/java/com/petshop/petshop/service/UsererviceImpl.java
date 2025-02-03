package com.petshop.petshop.service;

import com.petshop.petshop.DTO.ApiResponseDTO;
import com.petshop.petshop.DTO.UserDTO;
import com.petshop.petshop.exception.ResourceNotFoundException;
import com.petshop.petshop.model.User;
import com.petshop.petshop.repository.UserRepository;
import com.petshop.petshop.response.ApiResponseBuilder;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public ApiResponseDTO<List<User>> getAllUsers() {
        return listResponseBuilder.createSuccessResponse(this.userRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDTO<User> getUser(String id) {
        return responseBuilder.createSuccessResponse(this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id)));
    }

    @Override
    @Transactional(readOnly = false)
    public ApiResponseDTO<User> createUser(UserDTO requestNewUser) {
        System.out.println(requestNewUser);
        if(this.userRepository.findByLogin(requestNewUser.login()) != null) {
            throw new ValidationException("Email já cadastrado");
        };

        String encryptedPassword = new BCryptPasswordEncoder().encode(requestNewUser.password());

        User newUser = new User(requestNewUser.name(), requestNewUser.login(), requestNewUser.role(), encryptedPassword);

        System.out.println(newUser);

        return responseBuilder.createSuccessResponse(this.userRepository.save(newUser));
    }

    @Override
    @Transactional(readOnly = false)
    public ApiResponseDTO<User> updateUser(String id, UserDTO updateUser) {
        return responseBuilder.createSuccessResponse(this.userRepository.findById(id).map(user ->{
            user.setName(updateUser.name());
                user.setLogin(updateUser.login());
                if(!updateUser.password().equals("any")){
                    user.setPassword(new BCryptPasswordEncoder().encode(updateUser.password()));
                }
                user.setRole(updateUser.role());
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id)));
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteUser(String id) {
        this.userRepository.deleteById(id);
    }
}
