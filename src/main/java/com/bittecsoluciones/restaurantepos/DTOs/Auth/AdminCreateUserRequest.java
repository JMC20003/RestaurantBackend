package com.bittecsoluciones.restaurantepos.DTOs.Auth;

import lombok.Data;

import java.util.Set;

@Data
public class AdminCreateUserRequest {
    private String username;
    private String password;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private Set<String> roles; // ejemplo: ["WAITER","CHEF"]
}