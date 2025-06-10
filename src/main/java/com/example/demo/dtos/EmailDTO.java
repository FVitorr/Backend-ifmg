package com.example.demo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDTO {
    @NotBlank
    @Email
    private String to;
    @NotBlank
    private String subjet;
    @NotBlank
    private String body;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubjet() {
        return subjet;
    }

    public void setSubjet(String subjet) {
        this.subjet = subjet;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public EmailDTO(String subjet, String to, String body) {
        this.subjet = subjet;
        this.to = to;
        this.body = body;
    }

    public EmailDTO(){};

    @Override
    public String toString() {
        return "EmailDTO{" +
                "to='" + to + '\'' +
                ", subjet='" + subjet + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
