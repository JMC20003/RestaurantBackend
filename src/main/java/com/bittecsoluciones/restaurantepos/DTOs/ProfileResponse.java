package com.bittecsoluciones.restaurantepos.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private Long id;
    private String username;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private Set<String> roles; // incluimos roles para usar en guards del front
}
