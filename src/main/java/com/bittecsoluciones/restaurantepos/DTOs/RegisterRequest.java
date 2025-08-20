package com.bittecsoluciones.restaurantepos.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String address;
}