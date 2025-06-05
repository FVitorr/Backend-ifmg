package com.example.demo.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.junit.jupiter.api.Assertions;

import com.example.demo.dtos.ProdutoDTO;
import com.example.demo.entities.Produto;
import com.example.demo.repository.ProdutoRepository;
import com.example.demo.service.exceptions.ResourceNotFound;
import com.example.demo.util.Factory;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @InjectMocks
    public ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    private Long existingId;
    private Long nonExistingId;
    private PageImpl<Produto> page;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2L;
        Produto product = Factory.createProduto();
        product.setId(1L);
        page = new PageImpl<>(List.of(product));
    }

    @Test
    @DisplayName("Verificando se o objeto foi deletado no BD")
    public void deleteShouldDoNothingWhenIdExists() {
        when(produtoRepository.existsById(existingId)).thenReturn(true);
        doNothing().when(produtoRepository).deleteById(existingId);

        Assertions.assertDoesNotThrow(
                () -> produtoService.delete(existingId)
        );

        verify(produtoRepository, Mockito.times(1)).deleteById(existingId);

    }

    @Test
    @DisplayName("Verificando se levanta uma exceção, se o objeto não existe no BD")
    public void deleteShouldThrowExceptionWhenIdNonExists() {

        when(produtoRepository.existsById(nonExistingId)).thenReturn(false);

        Assertions.assertThrows(ResourceNotFound.class,
                () -> produtoService.delete(nonExistingId)
        );

        verify(produtoRepository, times(0)).deleteById(nonExistingId);

    }

    @Test
    @DisplayName("Verificando se o findAll retorna os dados paginados")
    public void findAllShouldReturnOnePage() {

        when(produtoRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Pageable pagina = PageRequest.of(0, 10);
        Page<ProdutoDTO> result = produtoService.findAll(pagina);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getContent().getFirst().getId());
        verify(produtoRepository, times(1)).findAll(pagina);

    }

    @Test
    @DisplayName("Verificando se o findAll retorna os dados paginados")
    void findbyIdShouçdReturnProductWhenIdExists() {
        Produto product = Factory.createProduto();
        product.setId(1L);
        when(produtoRepository.findById(existingId)).thenReturn(Optional.of(product));
        ProdutoDTO dto = produtoService.findById(existingId);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(existingId, dto.getId());
        verify(produtoRepository, times(1)).findById(existingId);
    }

}
