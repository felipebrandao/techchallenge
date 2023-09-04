package com.fiap.techchallenge.services;

import com.fiap.techchallenge.dtos.EletrodomesticoDTO;
import com.fiap.techchallenge.entities.Eletrodomestico;
import com.fiap.techchallenge.exceptions.EletrodomesticoCamposNaoPreenchidosException;
import com.fiap.techchallenge.exceptions.EletrodomesticoNaoEncontradoException;
import com.fiap.techchallenge.mappers.EletrodomesticoMapper;
import com.fiap.techchallenge.mappers.EnderecoMapper;
import com.fiap.techchallenge.repositories.EletrodomesticoRepository;
import com.fiap.techchallenge.services.impl.EletrodomesticoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EletrodomesticoServiceTest {

    private EletrodomesticoService eletrodomesticoService;

    @Spy
    private EletrodomesticoMapper eletrodomesticoMapper = Mappers.getMapper(EletrodomesticoMapper.class);

    @Mock
    private EletrodomesticoRepository eletrodomesticoRepository;

    private static final Long ID_EXISTENTE = 1L;

    @BeforeEach
    void setup() {
        eletrodomesticoService = new EletrodomesticoServiceImpl(eletrodomesticoRepository, eletrodomesticoMapper);
    }

    private EletrodomesticoDTO obterEletrodomesticoDTO() {
        EletrodomesticoDTO eletrodomesticoDTO = new EletrodomesticoDTO();

        eletrodomesticoDTO.setNome("Teste");
        eletrodomesticoDTO.setModelo("Teste");
        eletrodomesticoDTO.setPotencia(Double.valueOf(1L));

        return eletrodomesticoDTO;
    }

    @Test
    void testCadastrarEndereco() {
        EletrodomesticoDTO eletrodomesticoDTO = obterEletrodomesticoDTO();
        Eletrodomestico eletrodomestico = eletrodomesticoMapper.toEntity(eletrodomesticoDTO);

        when(eletrodomesticoRepository.save(Mockito.any())).thenReturn(eletrodomestico);

        EletrodomesticoDTO eletrodomesticoCadastrado = eletrodomesticoService.cadastrarEndereco(eletrodomesticoDTO);

        assertNotNull(eletrodomesticoCadastrado);
    }

    @Test
    void testAtualizarEletrodomesticoCamposNaoPreenchidos() {
        EletrodomesticoDTO eletrodomesticoDTO = new EletrodomesticoDTO();

        assertThrows(EletrodomesticoCamposNaoPreenchidosException.class, () -> {
            eletrodomesticoService.atualizarEletrodomestico(ID_EXISTENTE, eletrodomesticoDTO);
        });

        verify(eletrodomesticoRepository, never()).findById(ID_EXISTENTE);
        verify(eletrodomesticoRepository, never()).save(any());
    }

    @Test
    void testAtualizarEletrodomesticoNaoEncontrado() {
        EletrodomesticoDTO eletrodomesticoDTO = new EletrodomesticoDTO();
        eletrodomesticoDTO.setNome("Exemplo");

        when(eletrodomesticoRepository.findById(ID_EXISTENTE)).thenReturn(Optional.empty());

        assertThrows(EletrodomesticoNaoEncontradoException.class, () -> {
            eletrodomesticoService.atualizarEletrodomestico(ID_EXISTENTE, eletrodomesticoDTO);
        });

        verify(eletrodomesticoRepository, times(1)).findById(ID_EXISTENTE);
        verify(eletrodomesticoRepository, never()).save(any());
    }

    @Test
    void testAtualizarEletrodomestico() {
        EletrodomesticoDTO eletrodomesticoDTO = obterEletrodomesticoDTO();

        Eletrodomestico eletrodomesticoExistente = eletrodomesticoMapper.toEntity(eletrodomesticoDTO);
        when(eletrodomesticoRepository.findById(ID_EXISTENTE)).thenReturn(Optional.of(eletrodomesticoExistente));

        Eletrodomestico eletrodomesticoAtualizado = eletrodomesticoMapper.toEntity(eletrodomesticoDTO);
        when(eletrodomesticoRepository.save(any())).thenReturn(eletrodomesticoAtualizado);

        EletrodomesticoDTO eletrodomesticoAlterado = eletrodomesticoService.atualizarEletrodomestico(ID_EXISTENTE, eletrodomesticoDTO);

        assertNotNull(eletrodomesticoAlterado);
        assertEquals(eletrodomesticoDTO.getNome(), eletrodomesticoAlterado.getNome());
    }

    @Test
    void testGetEletrodomesticoByIdNaoEncontrado() {
        when(eletrodomesticoRepository.findById(ID_EXISTENTE)).thenReturn(Optional.empty());

        assertThrows(EletrodomesticoNaoEncontradoException.class, () -> {
            eletrodomesticoService.getEletrodomesticoById(ID_EXISTENTE);
        });

        verify(eletrodomesticoRepository, times(1)).findById(ID_EXISTENTE);
    }

    @Test
    void testGetEletrodomesticoById() {
        Eletrodomestico eletrodomesticoExistente = new Eletrodomestico();
        when(eletrodomesticoRepository.findById(ID_EXISTENTE)).thenReturn(Optional.of(eletrodomesticoExistente));

        EletrodomesticoDTO eletrodomesticoDTO = eletrodomesticoService.getEletrodomesticoById(ID_EXISTENTE);

        assertNotNull(eletrodomesticoDTO);
    }

    @Test
    void testDeletarEletrodomesticoNaoEncontrado() {
        when(eletrodomesticoRepository.findById(ID_EXISTENTE)).thenReturn(Optional.empty());

        assertThrows(EletrodomesticoNaoEncontradoException.class, () -> {
            eletrodomesticoService.deletarEletrodomestico(ID_EXISTENTE);
        });

        verify(eletrodomesticoRepository, times(1)).findById(ID_EXISTENTE);
        verify(eletrodomesticoRepository, never()).delete(any());
    }

    @Test
    void testDeletarEletrodomestico() {
        Eletrodomestico eletrodomesticoExistente = new Eletrodomestico();
        when(eletrodomesticoRepository.findById(ID_EXISTENTE)).thenReturn(Optional.of(eletrodomesticoExistente));

        eletrodomesticoService.deletarEletrodomestico(ID_EXISTENTE);

        verify(eletrodomesticoRepository, times(1)).findById(ID_EXISTENTE);
        verify(eletrodomesticoRepository, times(1)).delete(eletrodomesticoExistente);
    }

    @Test
    void testPesquisarEletrodomesticos() {
        String nome = "Exemplo";
        String modelo = "Modelo";
        Double potencia = 100.0;

        List<Eletrodomestico> eletrodomesticos = Collections.singletonList(new Eletrodomestico());
        when(eletrodomesticoRepository.pesquisarEletrodomesticos(nome, modelo, potencia)).thenReturn(eletrodomesticos);

        List<EletrodomesticoDTO> result = eletrodomesticoService.pesquisarEletrodomesticos(nome, modelo, potencia);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}