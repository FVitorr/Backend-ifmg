package com.example.demo.dtos;

import com.example.demo.entities.Categoria;

public class CategoriaDTO {

    private Long id;
    private String name;
    

    public CategoriaDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoriaDTO(Categoria entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }


    public CategoriaDTO(){};
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "CategoriaDTO [id=" + id + ", name=" + name + "]";
    }
    
}
