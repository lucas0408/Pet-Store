package com.petshop.petshop.DTO;

public record UserDTO(String name, String password, String role, String login) {
    @Override
    public String password() {
        return password;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String role() {
        return role;
    }

    @Override
    public String login() {
        return login;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
