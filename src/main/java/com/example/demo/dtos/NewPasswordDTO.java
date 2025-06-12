package com.example.demo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class NewPasswordDTO {

    @NotBlank(message = "Campo requerido")
    private String token;
    @NotBlank(message = "Campo requerido")
    private String newPassword;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public NewPasswordDTO(String token) {
        this.token = token;
    }


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
