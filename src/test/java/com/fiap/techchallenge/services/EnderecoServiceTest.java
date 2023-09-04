package com.fiap.techchallenge.services;

import com.fiap.techchallenge.dtos.EnderecoDTO;
import com.fiap.techchallenge.entities.Endereco;
import com.fiap.techchallenge.enums.EstadoEnum;
import com.fiap.techchallenge.exceptions.EnderecoExistenteException;
import com.fiap.techchallenge.exceptions.EnderecoNaoEncontradoException;
import com.fiap.techchallenge.mappers.EnderecoMapper;
import com.fiap.techchallenge.repositories.EnderecoRepository;
import com.fiap.techchallenge.services.impl.EnderecoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EnderecoServiceTest {

    private EnderecoService enderecoService;

    @Spy
    private EnderecoMapper enderecoMapper = Mappers.getMapper(EnderecoMapper.class);

    @Mock
    private EnderecoRepository enderecoRepository;

    private static final Long ID_EXISTENTE = 1L;

    @BeforeEach
    void setup() {
        enderecoService = new EnderecoServiceImpl(enderecoRepository, enderecoMapper);
    }

    @Test
    void testCadastrarEnderecoNaoExiste() {
        EnderecoDTO enderecoDTO = cadastrarEnderecoMock();


        when(enderecoRepository.isExisteEnderecoCadastrado(
                Mockito.anyString(),
                Mockito.anyInt(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(EstadoEnum.class)
        )).thenReturn(false);

        EnderecoDTO enderecoCadastrado = enderecoService.cadastrarEndereco(enderecoDTO);

        verify(enderecoRepository, times(1)).save(Mockito.any());

        assertNotNull(enderecoCadastrado);
        assertEquals(enderecoDTO.getRua(), enderecoCadastrado.getRua());
        assertEquals(enderecoDTO.getNumero(), enderecoCadastrado.getNumero());
        assertEquals(enderecoDTO.getBairro(), enderecoCadastrado.getBairro());
        assertEquals(enderecoDTO.getCidade(), enderecoCadastrado.getCidade());
        assertEquals(enderecoDTO.getEstado(), enderecoCadastrado.getEstado());
    }

    @Test
    void testCadastrarEnderecoEnderecoJaExiste() {
        cadastrarEnderecoMock();

        EnderecoExistenteException enderecoExistenteException = assertThrows(
                EnderecoExistenteException.class,
                () -> enderecoService.cadastrarEndereco(obterEnderecoDTO())
        );

        assertThat(enderecoExistenteException.getMessage())
                .isEqualTo("Endereço já cadastrado com esses campos.");

        verify(enderecoRepository, times(1)).isExisteEnderecoCadastrado(
                Mockito.anyString(),
                Mockito.anyInt(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(EstadoEnum.class)
        );

        verify(enderecoRepository, never()).save(Mockito.any());
    }

    @Test
    void testAtualizarEnderecoNaoEncontrado() {
        when(enderecoRepository.findById(ID_EXISTENTE)).thenReturn(Optional.empty());

        EnderecoNaoEncontradoException enderecoNaoEncontradoException = assertThrows(
                EnderecoNaoEncontradoException.class,
                () -> enderecoService.atualizarEndereco(ID_EXISTENTE, obterEnderecoDTO())
        );

        assertEquals(
                "Endereço com este id: " + ID_EXISTENTE + " não encontrado.",
                enderecoNaoEncontradoException.getMessage()
        );
    }

    @Test
    void testAtualizarEndereco() {
        Long idExistente = 1L;
        EnderecoDTO enderecoDTO = obterEnderecoDTO();

        Endereco enderecoExistente = new Endereco("Rua Antiga", 456,
                "Bairro Antigo", "Cidade Antiga", EstadoEnum.RJ);

        when(enderecoRepository.findById(idExistente)).thenReturn(Optional.of(enderecoExistente));
        when(enderecoRepository.save(Mockito.any())).thenReturn(enderecoExistente);

        EnderecoDTO enderecoAtualizado = enderecoService.atualizarEndereco(idExistente, enderecoDTO);

        assertNotNull(enderecoAtualizado);
        assertEquals(enderecoDTO.getRua(), enderecoAtualizado.getRua());
        assertEquals(enderecoDTO.getNumero(), enderecoAtualizado.getNumero());
        assertEquals(enderecoDTO.getBairro(), enderecoAtualizado.getBairro());
        assertEquals(enderecoDTO.getCidade(), enderecoAtualizado.getCidade());
        assertEquals(enderecoDTO.getEstado(), enderecoAtualizado.getEstado());
    }

    private EnderecoDTO obterEnderecoDTO() {
        EnderecoDTO enderecoDTO = new EnderecoDTO();

        enderecoDTO.setRua("Rua");
        enderecoDTO.setNumero(123);
        enderecoDTO.setBairro("Teste");
        enderecoDTO.setCidade("Teste");
        enderecoDTO.setEstado(EstadoEnum.SP);

        return enderecoDTO;
    }

    private EnderecoDTO cadastrarEnderecoMock() {
        EnderecoDTO enderecoDTO = obterEnderecoDTO();
        Endereco enderecoSalvo = enderecoMapper.toEntity(enderecoDTO);

        when(enderecoRepository.isExisteEnderecoCadastrado(
                Mockito.anyString(),
                Mockito.anyInt(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(EstadoEnum.class)
        )).thenReturn(true);

        when(enderecoRepository.save(Mockito.any())).thenReturn(enderecoSalvo);

        return enderecoDTO;
    }
}