package com.bittecsoluciones.restaurantepos.DTOs.Auth;

import com.bittecsoluciones.restaurantepos.Entity.Customer;

import java.time.LocalDate;

public record CustomerResponse(
        Long id,
        String name,
        String lastname,
        String email,
        String phone,
        String address,
        LocalDate birthDate,
        Integer loyaltyPoints
) {
    public static CustomerResponse from(Customer c) {
        return new CustomerResponse(
                c.getId(),
                c.getName(),
                c.getLastname(),
                c.getEmail(),
                c.getPhone(),
                c.getAddress(),
                c.getBirthDate(),
                c.getLoyaltyPoints()
        );
    }
}
