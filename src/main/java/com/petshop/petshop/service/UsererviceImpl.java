package com.petshop.petshop.service;

import com.petshop.petshop.model.User;
import com.petshop.petshop.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsererviceImpl implements UserService{

    @Autowired
    UserRepository userRepository;


    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> getUser(String id) {
        return this.userRepository.findById(id);
    }

    @Override
    public User createUser(User requestNewUser) {
        if(this.userRepository.findByLogin(requestNewUser.getLogin()) != null) {
            throw new ValidationException("Email já cadastrado");
        };

        String encryptedPassword = new BCryptPasswordEncoder().encode(requestNewUser.getPassword());

        User newUser = new User(requestNewUser.getLogin(), encryptedPassword, requestNewUser.getRole());

        return this.userRepository.save(newUser);
    }

    @Override
    public Optional<User> updateUser(String id, User updateUser) {
        return this.userRepository.findById(id).map(user ->{
                user.setLogin(updateUser.getLogin());
                user.setPassword(new BCryptPasswordEncoder().encode(updateUser.getPassword()));
                user.setRole(updateUser.getRole());
            return userRepository.save(user);
        });
    }

    @Override
    public void deleteUser(String id) {
        this.userRepository.deleteById(id);
    }
}
