package com.example.demo.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    @Test
    private void findAllShouldReturnSortByName() throws Exception{
        ResultActions resultActions =  mockMvc.perform(get("produto?page=0&size=10&sort=name, asc").accept(MediaType.APPLICATION_JSON));
    
        

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.content[0].nome").value("MacBook Pro"));
        resultActions.andExpect(jsonPath("$.content[1].nome").value("PC Gamer"));

    }
}
