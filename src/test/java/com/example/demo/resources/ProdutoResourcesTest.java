package com.example.demo.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.demo.dtos.ProdutoDTO;
import com.example.demo.repository.util.Factory;
import com.example.demo.service.ProdutoService;



@WebMvcTest(value = ProdutoResources.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ProdutoResourcesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProdutoService produtoService;

    private ProdutoDTO produtoDTO;
    private PageImpl<ProdutoDTO> page;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach void setUp(){
        existingId = 1L;
        nonExistingId = 200L;
        produtoDTO = Factory.createProdutoDTO();
        produtoDTO.setId(1L);
        page = new PageImpl<>(List.of(produtoDTO));
    }


    @Test
    void findAllShouldReturnAllPage() throws Exception {  
        when(produtoService.findAll(any())).thenReturn(page);
    
        ResultActions resultActions = 
            mockMvc.perform(get("/produto").accept("application/json"));
    
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findByIdShouldReturnProductWhenIdExists() throws Exception {
        when(produtoService.findById(existingId)).thenReturn(produtoDTO);
    
        ResultActions resultActions = 
            mockMvc.perform(get("/produto/{id}", existingId).accept("application/json"));
    
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(jsonPath("$.id").value(produtoDTO.getId()));          // Corrigido para '$.id'
        resultActions.andExpect(jsonPath("$.nome").value(produtoDTO.getNome()));      // Corrigido para '$.nome'
        resultActions.andExpect(jsonPath("$.descricao").value(produtoDTO.getDescricao()));  // Corrigido para '$.descricao'
    }
    

    
}
