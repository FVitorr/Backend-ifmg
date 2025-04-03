package com.example.demo.service.exceptions;

public class ResourceNotFound extends RuntimeException{
    
    public ResourceNotFound(){
        super();
    }

    public ResourceNotFound(String mensagem){
        super(mensagem);
    }
}
