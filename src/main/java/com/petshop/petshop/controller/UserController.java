package com.petshop.petshop.controller;

import com.petshop.petshop.DTO.ApiResponseDTO;
import com.petshop.petshop.model.User;
import com.petshop.petshop.response.ApiResponseBuilder;
import com.petshop.petshop.service.UserService;
import com.petshop.petshop.util.ResponseMessageProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private ApiResponseBuilder<List<User>> listResponseBuilder;

    @Autowired
    private ApiResponseBuilder<Optional<User>> responseBuilder;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<ApiResponseDTO<List<User>>> getAll(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(listResponseBuilder.createSuccessResponse(
                users
        ));
    }

    @PostMapping()
    public ResponseEntity<ApiResponseDTO<Optional<User>>> newUser(@RequestBody @Valid User requestNewUser){
        System.out.print(requestNewUser);
        Optional<User> newUser = Optional.ofNullable(this.userService.createUser(requestNewUser));
        return ResponseEntity.ok(responseBuilder.createSuccessResponse(
                newUser,
                messageProvider.getSuccessCreateMessage()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Optional<User>>> replaceUser(@PathVariable String id, @RequestBody @Valid User requestUpdateUser){
        System.out.println("oi");
        Optional<User> updateUser = this.userService.updateUser(id, requestUpdateUser);
        return ResponseEntity.ok(responseBuilder.createSuccessResponse(
                updateUser,
                messageProvider.getSuccessCreateMessage()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        this.userService.deleteUser(id);
        return ResponseEntity.ok(responseBuilder.createSuccessResponse(
                null
        ));
    }
}
