package com.fiap.techchallenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.dtos.EletrodomesticoDTO;
import com.fiap.techchallenge.services.EletrodomesticoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EletrodomesticoController.class)
class EletrodomesticoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EletrodomesticoService eletrodomesticoService;

    private EletrodomesticoDTO obterEletrodomesticoDTO(){
        EletrodomesticoDTO eletrodomesticoDTO = new EletrodomesticoDTO();

        eletrodomesticoDTO.setNome("Teste");
        eletrodomesticoDTO.setModelo("Teste");
        eletrodomesticoDTO.setPotencia(Double.valueOf(1L));

        return eletrodomesticoDTO;
    }
    @Test
    void cadastroSucesso() throws Exception {
        when(eletrodomesticoService.cadastrarEndereco(Mockito.any())).thenReturn(obterEletrodomesticoDTO());

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions result = mockMvc.perform(post("/eletrodomestico")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(obterEletrodomesticoDTO())));

        result.andExpect(status().isCreated());
    }
}