package com.petshop.petshop.controller;

import com.petshop.petshop.DTO.UserDTO;
import com.petshop.petshop.model.User;
import com.petshop.petshop.service.UserService;
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
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping()
    public ResponseEntity<User> newUser(@RequestBody @Valid UserDTO requestNewUser){
        System.out.println(requestNewUser);
        return ResponseEntity.ok(this.userService.createUser(requestNewUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> replaceUser(@PathVariable String id,
                                                                      @RequestBody @Valid UserDTO requestUpdateUser){
        return ResponseEntity.ok(this.userService.updateUser(id, requestUpdateUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        System.out.println(id);
        this.userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
