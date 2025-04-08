package com.example.demo.entities;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private double price;
    private String imageUrl;
    private Instant createdAt;
    private Instant updateAt;

    @ManyToMany
    @JoinTable(
        name="tb_produto_categoria", 
        joinColumns = @JoinColumn(name="product_id"),
        inverseJoinColumns = @JoinColumn(name="categoria_id"))
    private Set<Categoria> categorias = new HashSet<>();

    
    public Produto(Long id, String nome, String descricao, double price, String imageUrl) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Produto(Produto produto, Set<Categoria> categorias) {
        super()
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

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    @PrePersist
    private void prePersist(){
        createdAt = Instant.now();
    }

    @PreUpdate
    private void preUpdate(){
        updateAt = Instant.now();
    }
}
