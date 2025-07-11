package com.petshop.petshop.service;

import com.petshop.petshop.DTO.UserDTO;
import com.petshop.petshop.DTO.UserResponseDTO;
import com.petshop.petshop.exception.ResourceNotFoundException;
import com.petshop.petshop.model.User;
import com.petshop.petshop.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsererviceImpl implements UserService{

    @Autowired
    UserRepository userRepository;


    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return this.userRepository.findAll().stream().map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUser(String id) {
        return new UserResponseDTO(this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id)));
    }

    @Override
    @Transactional(readOnly = false)
    public UserResponseDTO createUser(UserDTO requestNewUser) {
        if(this.userRepository.findByLogin(requestNewUser.login()) != null) {
            throw new ValidationException("Email já cadastrado");
        };

        String encryptedPassword = new BCryptPasswordEncoder().encode(requestNewUser.password());

        User newUser = new User(requestNewUser.name(), requestNewUser.login(), requestNewUser.role(), encryptedPassword);

        System.out.println(newUser);

        return new UserResponseDTO(this.userRepository.save(newUser));
    }

    @Override
    @Transactional(readOnly = false)
    public UserResponseDTO updateUser(String id, UserDTO updateUser) {
        return new UserResponseDTO(this.userRepository.findById(id).map(user ->{
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
