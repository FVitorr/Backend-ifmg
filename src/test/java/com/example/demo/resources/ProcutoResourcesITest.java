package com.example.demo.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.example.demo.dtos.ProdutoDTO;
import com.example.demo.entities.Produto;
import com.example.demo.repository.util.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.repository.util;

import org.springframework.http.MediaType;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProcutoResourcesITest {
    

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private TokenUtil TokenUtil;
    private String userName;
    private String password;
    private String token; 

    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp( ) throws Exception{
        existingId = 1L;
        nonExistingId = 2000L;
    }

    @Test
    private void findAllShouldReturnSortByName() throws Exception{
        ResultActions resultActions =  mockMvc.perform(get("produto?page=0&size=10&sort=name, asc").accept(MediaType.APPLICATION_JSON));
    
        

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.content[0].nome").value("MacBook Pro"));
        resultActions.andExpect(jsonPath("$.content[1].nome").value("PC Gamer"));

    }

    @Test
    public void updateShouldReturnDtoIdExist()throws Exception {
        ProdutoDTO dto = Factory.createProdutoDTO();

        String dtoJson = objectMapper.writeValueAsString(dto);
        String nameExpected = dto.getNome();
        String descriptionExcept = dto.getDescricao();

        ResultActions resultActions = 
            mockMvc.perform(put("/produto/{id}", existingId).content(dtoJson).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
    
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").value(existingId));          
        resultActions.andExpect(jsonPath("$.nome").value(nameExpected));      
        resultActions.andExpect(jsonPath("$.descricao").value(descriptionExcept)); 

    }


    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist()throws Exception {
        ProdutoDTO dto = Factory.createProdutoDTO();

        String dtoJson = objectMapper.writeValueAsString(dto);

        ResultActions resultActions = 
            mockMvc.perform(put("/produto/{id}", nonExistingId).content(dtoJson).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
    
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnNewwObjectWhenDataAreCorrect() throws Exception{
        ProdutoDTO dto = Factory.createProdutoDTO();

        String dtoJson = objectMapper.writeValueAsString(dto);

        String nameExpected = dto.getNome();
        Long idLong = 26L;

        ResultActions resultActions = 
            mockMvc.perform(post("/produto").content(dtoJson).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
    
        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(jsonPath("$.id").value(idLong));          
        resultActions.andExpect(jsonPath("$.nome").value(nameExpected));
    }UleteShouldReturnNoContentWhenIdExists()throws Exception {
        ResultActions resultActions = 
            mockMvc.perform(delete("/produto/{id}", existingId));
    
        resultActions.andExpect(status().isNoContent());
    }


    @Test
    public void deleteShouldReturnNoFoundWhenIdDoesNotExists()throws Exception {

        ResultActions resultActions = 
            mockMvc.perform(delete("/produto/{id}", nonExistingId));
    
        resultActions.andExpect(status().isNotFound());
    }


    @Test
    public void findByIdShouldReturnProductWhenIdExists() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/produto/{id}",existingId).accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        String resultJson = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(resultJson);

        ProdutoDTO dto = objectMapper.readValue(resultJson, ProdutoDTO.class);

        Assertions.assertEquals(existingId,dto.getId());
    }
}
