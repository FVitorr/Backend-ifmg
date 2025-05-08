package com.example.demo.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.demo.entities.Produto;
import com.example.demo.repository.util.Factory;

import java.util.Optional;

@DataJpaTest
public class ProdutoRepositoryTest {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    private Long existingId;


    @BeforeEach
    void setUp() throws  Exception {
        existingId = 1L;
    }

    @Test
    @DisplayName(value = "Verificando se o objeto não existe no BD depois de deletado.")
    public void deleteShouldDeleteObjectWhenIdExists() {

        produtoRepository.deleteById(existingId);
        Optional<Produto> obj = produtoRepository.findById(existingId);

        Assertions.assertFalse(obj.isPresent());

    }


    @Test
    @DisplayName( value = "Verificando o autocrimento da chave primária.")
    public void insertShouldPersistWithAutoIncrementedIdWhenIdZero() {
        Produto product = Factory.createProduto();
        product.setId(null);

        Produto p = produtoRepository.save(product);
        Optional<Produto> obj = produtoRepository.findById(p.getId());
        Assertions.assertTrue(obj.isPresent());
        Assertions.assertNotEquals(0L, obj.get().getId());
        Assertions.assertEquals(26L, obj.get().getId());

    }

}
    