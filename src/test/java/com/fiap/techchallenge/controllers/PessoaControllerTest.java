package com.fiap.techchallenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.techchallenge.dtos.PessoaDTO;
import com.fiap.techchallenge.enums.SexoEnum;
import com.fiap.techchallenge.exceptions.PessoaNaoEncontradaException;
import com.fiap.techchallenge.services.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PessoaController.class)
class PessoaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    PessoaService pessoaService;

    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    void testCadastroSucesso() throws Exception {
        PessoaDTO pessoaDTO = obterPessoaDTO();
        when(pessoaService.cadastrarPessoa(Mockito.any())).thenReturn(pessoaDTO);

        ResultActions result = mockMvc.perform(post("/pessoa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaDTO)));

        result.andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(pessoaDTO)));
    }

    @Test
    void testCadastroFalha() throws Exception {
        when(pessoaService.cadastrarPessoa(Mockito.any())).thenThrow(new PessoaNaoEncontradaException("teste"));

        PessoaDTO pessoaDTO = obterPessoaDTO();
        ResultActions result = mockMvc.perform(post("/pessoa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaDTO)));

        result.andExpect(status().isNotFound());
    }

    @Test
    void testAtualizarSucesso() throws Exception {
        Long idExistente = 1L;
        PessoaDTO pessoaDTO = obterPessoaDTO();
        when(pessoaService.atualizarPessoa(idExistente, pessoaDTO)).thenReturn(pessoaDTO);

        ResultActions result = mockMvc.perform(put("/pessoa/{id}", idExistente)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaDTO)));

        result.andExpect(status().isOk());

        String responseJson = result.andReturn().getResponse().getContentAsString();
        String expectedJson = objectMapper.writeValueAsString(pessoaDTO);

        JSONAssert.assertEquals(expectedJson, responseJson, true);
    }

    @Test
    void testDeletarSucesso() throws Exception {
        Long idExistente = 1L;

        mockMvc.perform(delete("/pessoa/{id}", idExistente))
                .andExpect(status().isNoContent());

        verify(pessoaService, times(1)).deletarPessoa(idExistente);
    }

    @Test
    void testGetPessoaById() throws Exception {
        Long id = 1L;
        PessoaDTO pessoaDTO = obterPessoaDTO();

        when(pessoaService.getPessoaById(id)).thenReturn(pessoaDTO);

        mockMvc.perform(get("/pessoa/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pessoaDTO)));
    }

    @Test
    void testBuscarPessoasPorNomeECPF() throws Exception {
        String nome = "Maria";
        String cpf = "12345678900";

        List<PessoaDTO> pessoas = new ArrayList<>();
        PessoaDTO pessoaDTO = obterPessoaDTO();
        pessoas.add(pessoaDTO);

        when(pessoaService.buscarPessoasPorNomeECPF(eq(nome), eq(cpf))).thenReturn(pessoas);

        mockMvc.perform(get("/pessoa/buscar")
                        .param("nome", nome)
                        .param("cpf", cpf))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pessoas)));
    }


    private PessoaDTO obterPessoaDTO() {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("Teste");
        pessoaDTO.setCpf("54025833077");
        pessoaDTO.setSexo(SexoEnum.FEMININO);
        pessoaDTO.setDataDeNascimento(LocalDate.now());
        return pessoaDTO;
    }

}
