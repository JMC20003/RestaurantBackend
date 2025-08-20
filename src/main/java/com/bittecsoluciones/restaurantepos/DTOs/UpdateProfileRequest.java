package com.bittecsoluciones.restaurantepos.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank
    @Size(min = 2, max = 50)
    private String lastname;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6, max = 15)
    private String phone;
}
