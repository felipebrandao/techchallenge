package com.fiap.techchallenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.techchallenge.dtos.EnderecoDTO;
import com.fiap.techchallenge.enums.EstadoEnum;
import com.fiap.techchallenge.exceptions.EnderecoNaoEncontradoException;
import com.fiap.techchallenge.services.EnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.fiap.techchallenge.enums.EstadoEnum.SP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(EnderecoController.class)
class EnderecoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    EnderecoService enderecoService;

    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    void testGetEnderecoById() throws Exception {
        Long id = 1L;
        EnderecoDTO enderecoDTO = obterEnderecoDTO();

        when(enderecoService.getEnderecoById(id)).thenReturn(enderecoDTO);

        mockMvc.perform(get("/endereco/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(enderecoDTO)));
    }

    @Test
    void testCadastroEnderecoSucesso() throws Exception {
        EnderecoDTO enderecoDTO = obterEnderecoDTO();
        when(enderecoService.cadastrarEndereco(Mockito.any())).thenReturn(enderecoDTO);

        ResultActions result = mockMvc.perform(post("/endereco")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enderecoDTO)));

        result.andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(enderecoDTO)));
    }

    @Test
    void testCadastroEnderecoFalha() throws Exception {
        when(enderecoService.cadastrarEndereco(Mockito.any())).thenThrow(new EnderecoNaoEncontradoException("teste"));

        EnderecoDTO enderecoDTO = obterEnderecoDTO();
        ResultActions result = mockMvc.perform(post("/endereco")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enderecoDTO)));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void testAtualizarEnderecoSucesso() throws Exception {
        Long id = 1L;
        EnderecoDTO enderecoDTO = obterEnderecoDTO();

        when(enderecoService.atualizarEndereco(id, enderecoDTO)).thenReturn(enderecoDTO);

        mockMvc.perform(put("/endereco/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enderecoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rua").value(enderecoDTO.getRua()))
                .andExpect(jsonPath("$.numero").value(enderecoDTO.getNumero()))
                .andExpect(jsonPath("$.bairro").value(enderecoDTO.getBairro()))
                .andExpect(jsonPath("$.cidade").value(enderecoDTO.getCidade()))
                .andExpect(jsonPath("$.estado").value(enderecoDTO.getEstado().name()));
        assertThat(enderecoDTO.getRua()).isEqualTo("Rua Carlos Alberto Trevisan");
        assertThat(enderecoDTO.getNumero()).isEqualTo(400);
        assertThat(enderecoDTO.getBairro()).isEqualTo("Bela Vista");
        assertThat(enderecoDTO.getCidade()).isEqualTo("São Paulo");
        assertThat(enderecoDTO.getEstado()).isEqualTo(EstadoEnum.SP);
    }

    @Test
    void testDeletarEnderecoSucesso() throws Exception {
        Long idExistente = 1L;

        mockMvc.perform(delete("/endereco/{id}", idExistente))
                .andExpect(status().isNoContent());

        verify(enderecoService, times(1)).deletarEndereco(idExistente);
    }

    private EnderecoDTO obterEnderecoDTO() {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setRua("Rua Carlos Alberto Trevisan");
        enderecoDTO.setNumero(400);
        enderecoDTO.setBairro("Bela Vista");
        enderecoDTO.setCidade("São Paulo");
        enderecoDTO.setEstado(SP);

        return enderecoDTO;
    }
}