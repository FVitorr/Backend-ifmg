package com.example.demo.resources;

import com.example.demo.dtos.RequestTokenDTO;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthResources {

    @Autowired
    private AuthService authService;

    public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody RequestTokenDTO dto){
        authService.createRecoverToken(dto);
        return  ResponseEntity.noContent().build();
    }

    @PostMapping(value="new-password")
    public ResponseEntity<Void> saveNewPassword(@Valid @RequestBody RequestTokenDTO dto){
        authService.saveNewPassword(dto);
        return  ResponseEntity.noContent().build();
    }
}
