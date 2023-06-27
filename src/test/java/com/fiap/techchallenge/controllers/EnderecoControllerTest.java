package com.fiap.techchallenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.dtos.EnderecoDTO;
import com.fiap.techchallenge.enums.EstadoEnum;
import com.fiap.techchallenge.services.EnderecoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnderecoController.class)
class EnderecoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EnderecoService enderecoService;

    private EnderecoDTO obterEnderecoDTO(){
        EnderecoDTO enderecoDTO = new EnderecoDTO();

        enderecoDTO.setRua("Rua");
        enderecoDTO.setNumero(123);
        enderecoDTO.setBairro("Teste");
        enderecoDTO.setCidade("Teste");
        enderecoDTO.setEstado(EstadoEnum.SP);

        return enderecoDTO;
    }

    @Test
    void cadastroSucesso() throws Exception {
        when(enderecoService.cadastrarEndereco(Mockito.any())).thenReturn(obterEnderecoDTO());

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions result = mockMvc.perform(post("/endereco")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(obterEnderecoDTO())));

        result.andExpect(status().isCreated());

    }
}