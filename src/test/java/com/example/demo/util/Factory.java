package com.example.demo.util;

import com.example.demo.dtos.ProdutoDTO;
import com.example.demo.entities.Categoria;
import com.example.demo.entities.Produto;

public class Factory {
    
    public static Produto createProduto(){
        Produto p = new Produto();
        p.setName("Iphone");
        p.setPrice(1000);
        p.setImageUrl("aaa");
        p.getCategorias().add(new Categoria(1L, "Livros")); 
        
        return p; 
    }

    public static ProdutoDTO createProdutoDTO(){
        Produto p = createProduto();  
        ProdutoDTO dto = new ProdutoDTO(p); 
        return dto;
    }
}
