package com.fiap.techchallenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.dtos.EnderecoDTO;
import com.fiap.techchallenge.exceptions.EnderecoExistenteException;
import com.fiap.techchallenge.services.EnderecoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.fiap.techchallenge.enums.EstadoEnum.SP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private static final String ENDERECO_ENDPOINT = "/endereco";
    private static final Long ID_EXISTENTE = 1L;

    @Test
    void testGetEnderecoById() throws Exception {
        EnderecoDTO enderecoDTO = createEnderecoDTO();

        when(enderecoService.getEnderecoById(ID_EXISTENTE)).thenReturn(enderecoDTO);

        mockMvc.perform(get("/endereco/{id}", ID_EXISTENTE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(enderecoDTO)));
    }

    @Test
    void testCadastroEnderecoSucesso() throws Exception {
        EnderecoDTO enderecoDTO = createEnderecoDTO();
        when(enderecoService.cadastrarEndereco(any())).thenReturn(enderecoDTO);

        ResultActions result = mockMvc.perform(post(ENDERECO_ENDPOINT)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enderecoDTO)));

        result.andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(enderecoDTO)));
    }

    @Test
    void testRetornarBadRequestAoCadastrarEnderecoComDadosInvalidos() throws Exception {
        EnderecoDTO enderecoDTO = new EnderecoDTO();

        ResultActions result = mockMvc.perform(post(ENDERECO_ENDPOINT)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enderecoDTO)));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void testAtualizarEnderecoComSucesso() throws Exception {
        EnderecoDTO enderecoDTO = createEnderecoDTO();

        when(enderecoService.atualizarEndereco(eq(ID_EXISTENTE), any())).thenReturn(enderecoDTO);

        ResultActions result = mockMvc.perform(put(ENDERECO_ENDPOINT + "/{id}", ID_EXISTENTE)
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
    void testDeletarEnderecoComSucesso() throws Exception {
        ResultActions result = mockMvc.perform(delete(ENDERECO_ENDPOINT + "/{id}", ID_EXISTENTE));

        result.andExpect(status().isNoContent());

        verify(enderecoService, times(1)).deletarEndereco(eq(ID_EXISTENTE));
    }

    @Test
    void testCadastroEnderecoFalha() throws Exception {
        when(enderecoService.cadastrarEndereco(any())).thenThrow(new EnderecoExistenteException("teste"));

        EnderecoDTO enderecoDTO = createEnderecoDTO();
        ResultActions result = mockMvc.perform(post("/endereco")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enderecoDTO)));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void testAtualizarEnderecoSucesso() throws Exception {
        EnderecoDTO enderecoDTO = createEnderecoDTO();

        when(enderecoService.atualizarEndereco(eq(ID_EXISTENTE), any())).thenReturn(enderecoDTO);

        mockMvc.perform(put("/endereco/{id}", ID_EXISTENTE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enderecoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.rua").value(enderecoDTO.getRua()))
                .andExpect(jsonPath("$.numero").value(enderecoDTO.getNumero()))
                .andExpect(jsonPath("$.bairro").value(enderecoDTO.getBairro()))
                .andExpect(jsonPath("$.cidade").value(enderecoDTO.getCidade()))
                .andExpect(jsonPath("$.estado").value(enderecoDTO.getEstado().name()));

        assertEquals("Rua Carlos Alberto Trevisan", enderecoDTO.getRua());
        assertEquals(400, enderecoDTO.getNumero());
        assertEquals("Bela Vista", enderecoDTO.getBairro());
        assertEquals("São Paulo", enderecoDTO.getCidade());
        assertEquals(SP, enderecoDTO.getEstado());
    }

    @Test
    void testDeletarEnderecoSucesso() throws Exception {
        mockMvc.perform(delete("/endereco/{id}", ID_EXISTENTE))
                .andExpect(status().isNoContent());

        verify(enderecoService, times(1)).deletarEndereco(eq(ID_EXISTENTE));
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