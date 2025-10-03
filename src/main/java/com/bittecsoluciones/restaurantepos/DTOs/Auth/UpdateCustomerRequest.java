package com.bittecsoluciones.restaurantepos.DTOs.Auth;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateCustomerRequest{
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private Integer loyaltyPoints;
}