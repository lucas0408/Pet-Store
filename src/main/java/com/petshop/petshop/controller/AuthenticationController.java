package com.petshop.petshop.controller;

import com.petshop.petshop.DTO.AuthenticationDTO;
import com.petshop.petshop.DTO.LoginResponseDTO;
import com.petshop.petshop.infra.security.TokenService;
import com.petshop.petshop.model.User;
import com.petshop.petshop.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/login")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping()
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){

        System.out.println(data);


        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());


        return ResponseEntity.ok(new LoginResponseDTO(token, data.login()));
    }
}
