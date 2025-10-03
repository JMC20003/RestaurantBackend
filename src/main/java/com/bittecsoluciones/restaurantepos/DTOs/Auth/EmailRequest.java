package com.bittecsoluciones.restaurantepos.DTOs.Auth;

public class EmailRequest {
    private String email;

    public EmailRequest() {} // Constructor vacío necesario para Spring

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
