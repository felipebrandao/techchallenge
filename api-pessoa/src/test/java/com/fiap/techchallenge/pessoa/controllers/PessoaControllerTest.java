package com.fiap.techchallenge.pessoa.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.pessoa.dtos.PessoaDTO;
import com.fiap.techchallenge.pessoa.enums.SexoEnum;
import com.fiap.techchallenge.pessoa.exceptions.PessoaNaoEncontradaException;
import com.fiap.techchallenge.pessoa.services.PessoaService;
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

    private static final String PESSOA_ENDPOINT = "/api/pessoa";

    @Test
    void testCadastroSucesso() throws Exception {
        //given
        PessoaDTO pessoaDTO = obterPessoaDTO();
        when(pessoaService.cadastrarPessoa(Mockito.any())).thenReturn(pessoaDTO);

        //when
        ResultActions result = mockMvc.perform(post(PESSOA_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaDTO)));

        //then
        result.andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(pessoaDTO)));
    }

    @Test
    void testCadastroFalha() throws Exception {
        //given
        when(pessoaService.cadastrarPessoa(Mockito.any())).thenThrow(new PessoaNaoEncontradaException("teste"));

        //when
        PessoaDTO pessoaDTO = obterPessoaDTO();
        ResultActions result = mockMvc.perform(post(PESSOA_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaDTO)));

        //then
        result.andExpect(status().isNotFound());
    }

    @Test
    void testAtualizarSucesso() throws Exception {
        //given
        Long idExistente = 1L;
        PessoaDTO pessoaDTO = obterPessoaDTO();
        when(pessoaService.atualizarPessoa(idExistente, pessoaDTO)).thenReturn(pessoaDTO);

        //when
        ResultActions result = mockMvc.perform(put(PESSOA_ENDPOINT + "/{id}", idExistente)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaDTO)));

        //then
        result.andExpect(status().isOk());

        String responseJson = result.andReturn().getResponse().getContentAsString();
        String expectedJson = objectMapper.writeValueAsString(pessoaDTO);

        JSONAssert.assertEquals(expectedJson, responseJson, true);
    }

    @Test
    void testDeletarSucesso() throws Exception {
        //given
        Long idExistente = 1L;

        //when
        mockMvc.perform(delete(PESSOA_ENDPOINT + "/{id}", idExistente))
                .andExpect(status().isNoContent());

        //then
        verify(pessoaService, times(1)).deletarPessoa(idExistente);
    }

    @Test
    void testGetPessoaById() throws Exception {
        //given
        Long id = 1L;
        PessoaDTO pessoaDTO = obterPessoaDTO();

        when(pessoaService.getPessoaById(id)).thenReturn(pessoaDTO);

        //when
        mockMvc.perform(get(PESSOA_ENDPOINT + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pessoaDTO)));

        //then
        verify(pessoaService, times(1)).getPessoaById(id);
    }

    @Test
    void testBuscarPessoasPorNomeECPF() throws Exception {
        //given
        String nome = "Maria";
        String cpf = "12345678900";

        List<PessoaDTO> pessoas = new ArrayList<>();
        PessoaDTO pessoaDTO = obterPessoaDTO();
        pessoas.add(pessoaDTO);

        when(pessoaService.buscarPessoasPorNomeECPF(eq(nome), eq(cpf))).thenReturn(pessoas);

        //when
        mockMvc.perform(get(PESSOA_ENDPOINT + "/buscar")
                        .param("nome", nome)
                        .param("cpf", cpf))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pessoas)));

        //then
        verify(pessoaService, times(1)).buscarPessoasPorNomeECPF(eq(nome), eq(cpf));
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
