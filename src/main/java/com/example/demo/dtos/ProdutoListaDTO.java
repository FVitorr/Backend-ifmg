package com.example.demo.dtos;

import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.example.demo.entities.Produto;
import com.example.demo.projections.ProdutoProjection;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public class ProdutoListaDTO extends RepresentationModel<ProdutoListaDTO> {

    @Schema(description = "Database generated ID Produto")
    private Long id;
    @Schema(description = "Produto name")
    @Size(min = 3, max = 255, message = "Dever ter entre 3 e 255 caracteres")
    private String name;
    @Schema(description = "Produto price")
    @Positive(message = "Preço deve ser positivo")
    private double price;
    @Schema(description = "Produto url of the image")
    private String imageUrl;


    public ProdutoListaDTO() {

    }

    public ProdutoListaDTO(Long id, String name, double price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProdutoListaDTO(Produto entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.imageUrl = entity.getImageUrl();
    }

    public ProdutoListaDTO(ProdutoProjection projection){
        this.id = projection.getId();
        this.name = projection.getName();
        this.price = projection.getPrice();
        this.imageUrl = projection.getImageUrl();
    }

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProdutoListaDTO that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProdutoListaDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
} 