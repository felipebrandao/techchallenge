package com.fiap.techchallenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.dtos.MoradorDTO;
import com.fiap.techchallenge.services.MoradorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MoradorController.class)
public class MoradorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MoradorService moradorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegistrarMorador() throws Exception {
        MoradorDTO moradorDTO = new MoradorDTO();
        moradorDTO.setParentesco("Parentesco do Morador");
        moradorDTO.setIdPessoa(1L);
        moradorDTO.setIdEndereco(2L);

        MoradorDTO novoMorador = new MoradorDTO();
        novoMorador.setId(1L);
        novoMorador.setParentesco("Parentesco do Morador");
        novoMorador.setIdPessoa(1L);
        novoMorador.setIdEndereco(2L);

        when(moradorService.criarMorador(any())).thenReturn(novoMorador);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/morador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moradorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andReturn();

        verify(moradorService, times(1)).criarMorador(any());
    }

    @Test
    public void testPesquisarMoradores() throws Exception {
        List<MoradorDTO> moradores = new ArrayList<>();
        MoradorDTO morador1 = new MoradorDTO();
        morador1.setId(1L);
        morador1.setParentesco("Morador 1");
        morador1.setIdPessoa(1L);
        morador1.setIdEndereco(2L);
        moradores.add(morador1);

        MoradorDTO morador2 = new MoradorDTO();
        morador2.setId(2L);
        morador2.setParentesco("Morador 2");
        morador2.setIdPessoa(3L);
        morador2.setIdEndereco(4L);
        moradores.add(morador2);

        when(moradorService.pesquisarMoradores(any(), any())).thenReturn(moradores);

        mockMvc.perform(MockMvcRequestBuilders.get("/morador/pesquisar")
                        .param("idPessoa", "1")
                        .param("idEndereco", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].parentesco").value("Morador 1"))
                .andExpect(jsonPath("$[0].idPessoa").value(1))
                .andExpect(jsonPath("$[0].idEndereco").value(2))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].parentesco").value("Morador 2"))
                .andExpect(jsonPath("$[1].idPessoa").value(3))
                .andExpect(jsonPath("$[1].idEndereco").value(4));

        verify(moradorService, times(1)).pesquisarMoradores(any(), any());
    }

}
