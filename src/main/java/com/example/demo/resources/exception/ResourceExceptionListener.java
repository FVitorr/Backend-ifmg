package com.example.demo.resources.exception;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.service.exceptions.ResourceNotFound;


@ControllerAdvice
public class  ResourceExceptionListener {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<StandartErro> resourceNotFound(){
        
    }
}