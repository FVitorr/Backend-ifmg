package com.example.demo.dtos;

import com.example.demo.entities.User;

public class UserInsertDTO extends UserDTO{
    private String password;

    public UserInsertDTO() {
        super();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
