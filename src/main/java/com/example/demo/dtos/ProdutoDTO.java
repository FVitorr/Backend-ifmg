package com.example.demo.dtos;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.entities.Categoria;
import com.example.demo.entities.Produto;

public class ProdutoDTO {

    private Long id;

    private String nome;
    private String descricao;
    private double price;
    private String imageUrl;
    private Instant createdAt;
    private Instant updateAt;

    private Set<CategoriaDTO> categorias = new HashSet<>();

    public ProdutoDTO(){}

    
    public ProdutoDTO(Long id, String nome, String descricao, double price, String imageUrl) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProdutoDTO(Produto entity){
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.descricao = entity.getDescricao();
        this.price=entity.getPrice();
        this.imageUrl = entity.getImageUrl();

        entity.getCategorias().stream().forEach(c -> this.categorias.add(new CategoriaDTO(c)));
    }

    public ProdutoDTO(Produto produto, Set<Categoria> categorias) {
        this(produto);
        categorias.forEach(c ->
            this.categorias.add(new CategoriaDTO(c))
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public Set<CategoriaDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<CategoriaDTO> categories){
        this.categorias = categories;
    }
}
