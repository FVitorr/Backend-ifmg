package com.example.demo.resources;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.demo.dtos.ProdutoDTO;
import com.example.demo.service.ProdutoService;
import com.example.demo.util.Factory;

@WebMvcTest(value = ProdutoResources.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class ProductResourceTest {

    // Responsável pelas Requisições, a qual quero testar.
    @Autowired
    private MockMvc mockMvc;

    // Camada que quero mocar.
    @MockitoBean
    private ProdutoService productService;

    private ProdutoDTO productDTO;
    private PageImpl<ProdutoDTO> page;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2000L;
        productDTO = Factory.createProdutoDTO();
        productDTO.setId(1L);
        page = new PageImpl<>(List.of(productDTO));
    }

    @Test
    void findAllShouldReturnAllPage() throws Exception {

        // Criar o método mocado
        when(productService.findAll(any())).thenReturn(page);

        // Teste a requisição
        ResultActions result =
                mockMvc.perform(get("/produto").accept("application/json"));

        // Analisa o resultado
        result.andExpect(status().isOk());

    }

    @Test
    void findByIdShouldReturnProductWhenIdExists() throws Exception {

        // Criar o método mocado
        when(productService.findById(existingId)).thenReturn(productDTO);

        // Teste a requisição
        ResultActions result =
                mockMvc.perform(get("/produto/{id}", existingId).accept("application/json"));

        // Analisa o resultado
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(productDTO.getId()));
        result.andExpect(jsonPath("$.nome").value(productDTO.getNome()));
        result.andExpect(jsonPath("$.descricao").value(productDTO.getDescricao()));

    }
}