package com.example.demo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public class RequestTokenDTO {

    @NotBlank(message = "Campo requerido")
    @Email(message = "E-mail Invalido")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public RequestTokenDTO(String email) {
        this.email = email;
    }
}
