package com.fiap.techchallenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.dtos.EnderecoDTO;
import com.fiap.techchallenge.services.EnderecoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.fiap.techchallenge.enums.EstadoEnum.SP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(EnderecoController.class)
class EnderecoControllerTest {

    private static final String ENDERECO_ENDPOINT = "/endereco";
    private static final Long EXISTING_ID = 1L;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    EnderecoService enderecoService;

    @Test
    void deveRetornarEnderecoPeloId() throws Exception {
        EnderecoDTO enderecoDTO = createEnderecoDTO();

        when(enderecoService.getEnderecoById(EXISTING_ID)).thenReturn(enderecoDTO);

        ResultActions result = mockMvc.perform(get(ENDERECO_ENDPOINT + "/{id}", EXISTING_ID));

        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(enderecoDTO)));
    }

    @Test
    void deveCadastrarEnderecoComSucesso() throws Exception {
        EnderecoDTO enderecoDTO = createEnderecoDTO();

        when(enderecoService.cadastrarEndereco(any())).thenReturn(enderecoDTO);

        ResultActions result = mockMvc.perform(post(ENDERECO_ENDPOINT)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enderecoDTO)));

        result.andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(enderecoDTO)));
    }

    @Test
    void deveRetornarBadRequestAoCadastrarEnderecoComDadosInvalidos() throws Exception {
        EnderecoDTO enderecoDTO = new EnderecoDTO();

        ResultActions result = mockMvc.perform(post(ENDERECO_ENDPOINT)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enderecoDTO)));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void deveAtualizarEnderecoComSucesso() throws Exception {
        EnderecoDTO enderecoDTO = createEnderecoDTO();
        when(enderecoService.atualizarEndereco(eq(EXISTING_ID), any())).thenReturn(enderecoDTO);

        ResultActions result = mockMvc.perform(put(ENDERECO_ENDPOINT + "/{id}", EXISTING_ID)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enderecoDTO)));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.rua").value(enderecoDTO.getRua()))
                .andExpect(jsonPath("$.numero").value(enderecoDTO.getNumero()))
                .andExpect(jsonPath("$.bairro").value(enderecoDTO.getBairro()))
                .andExpect(jsonPath("$.cidade").value(enderecoDTO.getCidade()))
                .andExpect(jsonPath("$.estado").value(enderecoDTO.getEstado().name()));

        assertThat(enderecoDTO.getRua()).isEqualTo("Rua Carlos Alberto Trevisan");
        assertThat(enderecoDTO.getNumero()).isEqualTo(400);
        assertThat(enderecoDTO.getBairro()).isEqualTo("Bela Vista");
        assertThat(enderecoDTO.getCidade()).isEqualTo("São Paulo");
        assertThat(enderecoDTO.getEstado()).isEqualTo(SP);
    }

    @Test
    void deveDeletarEnderecoComSucesso() throws Exception {
        ResultActions result = mockMvc.perform(delete(ENDERECO_ENDPOINT + "/{id}", EXISTING_ID));

        result.andExpect(status().isNoContent());

        verify(enderecoService, times(1)).deletarEndereco(eq(EXISTING_ID));
    }

    private EnderecoDTO createEnderecoDTO() {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setRua("Rua Carlos Alberto Trevisan");
        enderecoDTO.setNumero(400);
        enderecoDTO.setBairro("Bela Vista");
        enderecoDTO.setCidade("São Paulo");
        enderecoDTO.setEstado(SP);

        return enderecoDTO;
    }
}