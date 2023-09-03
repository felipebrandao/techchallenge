package com.fiap.techchallenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.techchallenge.dtos.EletrodomesticoDTO;
import com.fiap.techchallenge.services.EletrodomesticoService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EletrodomesticoController.class)
class EletrodomesticoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    EletrodomesticoService eletrodomesticoService;

    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    void testRegistrarEletrodomestico() throws Exception {
        when(eletrodomesticoService.cadastrarEndereco(Mockito.any())).thenReturn(obterEletrodomesticoDTO());

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions result = mockMvc.perform(post("/eletrodomestico")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(obterEletrodomesticoDTO())));

        result.andExpect(status().isCreated());
    }

    @Test
    public void testAtualizarCadastroEletrodomesticoPorId() throws Exception {
        Long idExistente = 1L;
        EletrodomesticoDTO eletrodomesticoDTO = obterEletrodomesticoDTO();

        when(eletrodomesticoService.atualizarEletrodomestico(
                idExistente, eletrodomesticoDTO)).thenReturn(eletrodomesticoDTO);

        ResultActions result = mockMvc.perform(put("/eletrodomestico/{id}", idExistente)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eletrodomesticoDTO)));

        result.andExpect(status().isOk());

        String responseJson = result.andReturn().getResponse().getContentAsString();
        String expectedJson = objectMapper.writeValueAsString(eletrodomesticoDTO);

        JSONAssert.assertEquals(expectedJson, responseJson, true);
    }

    @Test
    public void testBuscarEletrodomesticoPorId() throws Exception {
        Long id = 1L;
        EletrodomesticoDTO eletrodomesticoDTO = obterEletrodomesticoDTO();
        when(eletrodomesticoService.getEletrodomesticoById(id)).thenReturn(eletrodomesticoDTO);

        mockMvc.perform(get("/eletrodomestico/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(eletrodomesticoDTO)));
    }

    @Test
    public void testExcluirEletrodomesticoPorid() throws Exception {
        Long idExistente = 1L;

        mockMvc.perform(delete("/eletrodomestico/{id}", idExistente))
                .andExpect(status().isNoContent());

        verify(eletrodomesticoService, times(1)).deletarEletrodomestico(idExistente);
    }

    @Test
    public void testPesquisarEletrodomestico() throws Exception {
        EletrodomesticoDTO eletrodomesticoDTO = new EletrodomesticoDTO();
        eletrodomesticoDTO.setNome("TV");
        eletrodomesticoDTO.setModelo("Sony");
        eletrodomesticoDTO.setPotencia(200.0);

        when(eletrodomesticoService.pesquisarEletrodomesticos(eletrodomesticoDTO))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/eletrodomestico/pesquisar")
                .param("nome", eletrodomesticoDTO.getNome())
                .param("modelo", eletrodomesticoDTO.getModelo())
                .param("potencia", eletrodomesticoDTO.getPotencia().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private EletrodomesticoDTO obterEletrodomesticoDTO() {
        EletrodomesticoDTO eletrodomesticoDTO = new EletrodomesticoDTO();
        eletrodomesticoDTO.setNome("Teste");
        eletrodomesticoDTO.setModelo("Teste");
        eletrodomesticoDTO.setPotencia(Double.valueOf(1L));
        return eletrodomesticoDTO;
    }
}