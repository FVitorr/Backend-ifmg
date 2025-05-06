package com.example.demo.dtos;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.example.demo.entities.Categoria;
import com.example.demo.entities.Produto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name ="tb_product")
public class ProdutoDTO extends RepresentationModel<ProdutoDTO> {
    
    @Schema(description = "Database generated Id product")
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Product name")
    private String nome;
    @Schema(description = "Detailed description of the product")
    private String descricao;
    @Schema(description = "Product price")
    private double price;
    @Schema(description = "Product URL of image")
    private String imageUrl;
    private Instant createdAt;
    private Instant updateAt;

    @Schema(description = "Product categories (one or more)")
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
        this.nome = entity.getName();
        this.descricao = entity.getDescription();
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

    public Long getId(){
        return id;
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
