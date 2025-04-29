package com.example.demo.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.entities.Produto;

@DataJpaTest
public class ProdutoRepositoryTest {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    @DisplayName( value = "Verificando se o objeto não existe no DB depois de deletar")
    public void deleteShoudDeleteObjectWhenIdExists(){
        produtoRepository.deleteById(1L);
        Optional<Produto> obj = produtoRepository.findById(1l);
        
        Assertions.assertFalse(obj.isPresent());
    }
}
    