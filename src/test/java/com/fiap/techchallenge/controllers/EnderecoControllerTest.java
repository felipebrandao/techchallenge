package com.fiap.techchallenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.dtos.EnderecoDTO;
import com.fiap.techchallenge.enums.EstadoEnum;
import com.fiap.techchallenge.exceptions.EnderecoNaoEncontradoException;
import com.fiap.techchallenge.services.EnderecoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(EnderecoController.class)
class EnderecoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EnderecoService enderecoService;

//    @Test
//    void obterCadastroSucesso() throws Exception {
//        List<EnderecoDTO> enderecos = new ArrayList<>();
//        enderecos.add(new EnderecoDTO());
//        enderecos.add(new EnderecoDTO());
//
//        when(enderecoService.obterEnderecos()).thenReturn(enderecos);
//
//        ResultActions result = mockMvc.perform(get("/endereco"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(enderecos.size()));
//
//        result.andExpect(status().isOk());
//
//        verify(enderecoService, times(1)).obterEnderecos();
//    }

    @Test
    void cadastroSucesso() throws Exception {
        when(enderecoService.cadastrarEndereco(any())).thenReturn(obterEnderecoDTO());

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions result = mockMvc.perform(post("/endereco")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(obterEnderecoDTO())));

        result.andExpect(status().isCreated());
    }

    @Test
    public void deletarCadastroSucesso() throws Exception {
        Long enderecoId = 1L;

        doNothing().when(enderecoService).deletarEndereco(eq(enderecoId));

        ResultActions result = mockMvc.perform(delete("/endereco/{id}", enderecoId))
                .andExpect(status().isOk())
                .andExpect(content().string("Endereço deletado com sucesso"));

        result.andExpect(status().isOk());

        verify(enderecoService, times(1)).deletarEndereco(eq(enderecoId));
    }

    @Test
    public void deveEstourarExcecaoEnderecoNaoEncontradoAoDeletar() throws Exception {
        Long enderecoId = 1L;

        doThrow(EnderecoNaoEncontradoException.class).when(enderecoService).deletarEndereco(eq(enderecoId));

        ResultActions result = mockMvc.perform(delete("/endereco/{id}", enderecoId))
                .andExpect(status().isNotFound());

        result.andExpect(status().isNotFound());

        verify(enderecoService, times(1)).deletarEndereco(eq(enderecoId));
    }

    private EnderecoDTO obterEnderecoDTO() {
        EnderecoDTO enderecoDTO = new EnderecoDTO();

        enderecoDTO.setRua("Rua Carlos Alberto Trevisan");
        enderecoDTO.setNumero(400);
        enderecoDTO.setBairro("Bela Vista");
        enderecoDTO.setCidade("São Paulo");
        enderecoDTO.setEstado(EstadoEnum.SP);

        return enderecoDTO;
    }
}