package com.petshop.petshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PetshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetshopApplication.class, args);
    }
}