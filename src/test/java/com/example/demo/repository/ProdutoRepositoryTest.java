package com.example.demo.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.entities.Produto;
import com.example.demo.repository.util.Factory;

@DataJpaTest
public class ProdutoRepositoryTest {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    @DisplayName(value = "Verificando se o objeto existe no bd depois do delete")
    public void deleteShouldDeleteObjectWhenIdExists(){
        produtoRepository.deleteById(1L);
        Optional<Produto> obj = produtoRepository.findById(1L);
        Assertions.assertFalse(obj.isPresent());    
    }

    public void insertShouldPersistWhenAutoIncrementIdWhenIdNull(){
        Produto produto = Factory.createProduto();
        produto.setId(null);
        Produto p = produtoRepository.save(produto);
        Optional<Produto> obj = produtoRepository.findById(p.getId());
        Assertions.assertTrue(obj.isPresent());
        Assertions.assertNotEquals(0, obj.get().getId());
        Assertions.assertEquals(26, obj.get().getId());;
    }
}
    