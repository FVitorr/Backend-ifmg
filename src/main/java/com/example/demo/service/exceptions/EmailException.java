package com.example.demo.service.exceptions;

public class EmailException extends RuntimeException{
    public EmailException(String mensagem){
        super(mensagem);
    }
}
