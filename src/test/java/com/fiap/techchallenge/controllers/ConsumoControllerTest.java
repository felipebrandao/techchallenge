package com.fiap.techchallenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.techchallenge.dtos.ConsumoDTO;
import com.fiap.techchallenge.services.ConsumoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;

@WebMvcTest(ConsumoController.class)
class ConsumoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConsumoService consumoService;

    @BeforeEach
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    private ConsumoDTO obterConsumoDTO() {
        ConsumoDTO consumoDTO = new ConsumoDTO();
        consumoDTO.setIdEletrodomestico(1L);
        consumoDTO.setTempoConsumo(2.5);
        consumoDTO.setIdMorador(3L);
        consumoDTO.setDataConsumo(LocalDate.now());
        return consumoDTO;
    }

    @Test
    public void testRegistrarConsumo() throws Exception {
        ConsumoDTO consumoDTO = obterConsumoDTO();

        Mockito.when(consumoService.registrarConsumo(Mockito.any(ConsumoDTO.class)))
                .thenReturn(consumoDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/consumo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(consumoDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testPesquisarConsumos() throws Exception {
        LocalDate dataInicio = LocalDate.now().minusDays(30);
        LocalDate dataFim = LocalDate.now();
        Long idPessoa = 1L;
        Long idEndereco = 2L;
        Long idEletrodomestico = 3L;

        Mockito.when(consumoService.pesquisarConsumos(dataInicio, dataFim, idPessoa, idEndereco, idEletrodomestico))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/consumo/pesquisar")
                        .param("dataInicio", dataInicio.toString())
                        .param("dataFim", dataFim.toString())
                        .param("idPessoa", idPessoa.toString())
                        .param("idEndereco", idEndereco.toString())
                        .param("idEletrodomestico", idEletrodomestico.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testEncontrarConsumoPorId() throws Exception {
        Long idConsumo = 1L;
        ConsumoDTO consumoDTO = new ConsumoDTO();

        Mockito.when(consumoService.encontrarConsumoPorId(idConsumo))
                .thenReturn(consumoDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/consumo/{id}", idConsumo))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeletarConsumo() throws Exception {
        Long idConsumo = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/consumo/{id}", idConsumo))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}