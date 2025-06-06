package com.example.demo.dtos;

import java.util.Objects;

import com.example.demo.entities.Role;

public class RoleDTO {

    private Long id;

    private  String authority;

    public RoleDTO(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public RoleDTO() {}

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.authority = role.getAuthority();
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RoleDTO roleDTO)) return false;
        return Objects.equals(id, roleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                '}';
    }
}
