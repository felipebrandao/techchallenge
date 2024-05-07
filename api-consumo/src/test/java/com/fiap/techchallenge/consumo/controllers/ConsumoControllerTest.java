package com.fiap.techchallenge.consumo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.consumo.dtos.ConsumoDTO;
import com.fiap.techchallenge.consumo.services.ConsumoService;
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

    private static final String CONSUMO_ENDPOINT = "/api/consumo";

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

        mockMvc.perform(MockMvcRequestBuilders.post(CONSUMO_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(consumoDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testPesquisarConsumos() throws Exception {
        LocalDate dataInicio = LocalDate.now().minusDays(30);
        LocalDate dataFim = LocalDate.now();
        Long idPessoa = 1L;
        Long idEletrodomestico = 3L;

        Mockito.when(consumoService.pesquisarConsumos(dataInicio, dataFim, idPessoa, idEletrodomestico))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get(CONSUMO_ENDPOINT + "/pesquisar")
                        .param("dataInicio", dataInicio.toString())
                        .param("dataFim", dataFim.toString())
                        .param("idPessoa", idPessoa.toString())
                        .param("idEletrodomestico", idEletrodomestico.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testEncontrarConsumoPorId() throws Exception {
        Long idConsumo = 1L;
        ConsumoDTO consumoDTO = new ConsumoDTO();

        Mockito.when(consumoService.encontrarConsumoPorId(idConsumo))
                .thenReturn(consumoDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(CONSUMO_ENDPOINT + "/{id}", idConsumo))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeletarConsumo() throws Exception {
        Long idConsumo = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete(CONSUMO_ENDPOINT+ "/{id}", idConsumo))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
