package com.fiap.techchallenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fiap.techchallenge.dtos.PessoaDTO;
import com.fiap.techchallenge.enums.SexoEnum;
import com.fiap.techchallenge.services.PessoaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PessoaController.class)
class PessoaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PessoaService pessoaService;

    private PessoaDTO obterPessoaDTO() {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("Teste");
        pessoaDTO.setCpf("54025833077");
        pessoaDTO.setSexo(SexoEnum.FEMININO);
        pessoaDTO.setDataDeNascimento(LocalDate.now());
        return pessoaDTO;
    }

    @Test
    void cadastroSucesso() throws Exception {
        when(pessoaService.cadastrarPessoa(Mockito.any())).thenReturn(obterPessoaDTO());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JSR310Module());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ResultActions result = mockMvc.perform(post("/pessoa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(obterPessoaDTO())));

        result.andExpect(status().isCreated());
    }
}