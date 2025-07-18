package com.petshop.petshop;

import com.petshop.petshop.DTO.*;
import com.petshop.petshop.exception.ResourceNotFoundException;
import com.petshop.petshop.model.Category;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.model.User;
import com.petshop.petshop.repository.UserRepository;
import com.petshop.petshop.service.UsererviceImpl;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UsererviceImpl userService;

    @Test
    @DisplayName("Should get all users")
    public void getAllUsersHappyFlow(){
        List<User> produtos = new ArrayList<>();

        User user = createUser();
        produtos.add(user);

        List<UserResponseDTO> usersDTO = new ArrayList<>();
        usersDTO.add(new UserResponseDTO(user));

        given(userRepository.findAll()).willReturn(produtos);

        List<UserResponseDTO> getAllUsers = userService.getAllUsers();

        assertEquals(usersDTO, getAllUsers);
    }

    @Test
    @DisplayName("Should get user by id")
    public void getUserByIdHappyFlow(){

        User user = createUser();

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        UserResponseDTO userDTO = new UserResponseDTO(user);

        UserResponseDTO getUsersDTO = userService.getUser(user.getId());

        assertEquals(getUsersDTO, userDTO);
    }

    @Test
    @DisplayName("Should not get user by id")
    public void NotGetUserById(){


        given(userRepository.findById("f47ac10b-58cc-4372-a567-0e02b2c3d479")).willReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> userService.getUser("f47ac10b-58cc-4372-a567-0e02b2c3d479")
        );
    }

    @Test
    @DisplayName("Should create a user")
    public void CreateUserByHappyFlow(){

        UserDTO userDTO = createUserDTO();
        User user = createUser();

        given(userRepository.save(any(User.class))).willReturn(user);

        UserResponseDTO userResponseDTO = userService.createUser(userDTO);

        System.out.println(user);

        assertEquals(userResponseDTO, new UserResponseDTO(user));

    }

    @Test
    @DisplayName("User with exist login in DB")
    public void CreateUserWithExistLogin(){

        User user = createUser();

        UserDTO userDTO = createUserDTO();

        given(this.userRepository.findByLogin(userDTO.login())).willReturn(user);

        assertThrows(
                ValidationException.class,
                () -> userService.createUser(userDTO)
        );

        verify(userRepository, never())
                .save(any(User.class));
    }

    @Test
    @DisplayName("Should update a user")
    public void UpdateUserByHappyFlow(){

        UserDTO updateUserDTO = createUserDTO();

        User user = createUser();

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(userRepository.save(any(User.class))).willReturn(user);

        UserResponseDTO userResponseDTO = userService.updateUser(user.getId(), updateUserDTO);

        assertEquals(userResponseDTO, new UserResponseDTO(user));
    }

    @Test
    @DisplayName("Should not update a user with invalid ID")
    public void UpdateWithInvalidId(){

        UserDTO updateUserDTO = createUserDTO();

        User user = createUser();

        given(userRepository.findById(user.getId())).willReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> userService.updateUser(user.getId(), updateUserDTO)
        );

        verify(userRepository, never())
                .save(any(User.class));
    }

    @Test
    @DisplayName("Should delete a User")
    public void DeleteUserWithValidID(){

        User user = createUser();

        userService.deleteUser(user.getId());

        verify(userRepository)
                .deleteById(user.getId());
    }

    private User createUser(){
        User user = new User(
                "Lucas Gabriel",
                "lucas@email.com",
                "admin",
                "senha123"
        );
        user.setId("123e4567-e89b-12d3-a456-426614174000");
        return user;

    }

    private UserDTO createUserDTO(){
        return new UserDTO("Lucas Gabriel", "senha123", "admin", "lucas@email.com");
    }
}
