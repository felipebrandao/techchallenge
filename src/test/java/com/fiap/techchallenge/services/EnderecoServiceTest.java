package com.fiap.techchallenge.services;

import com.fiap.techchallenge.dtos.EnderecoDTO;
import com.fiap.techchallenge.entities.Endereco;
import com.fiap.techchallenge.enums.EstadoEnum;
import com.fiap.techchallenge.exceptions.EnderecoCamposNaoPreenchidosException;
import com.fiap.techchallenge.exceptions.EnderecoExistenteException;
import com.fiap.techchallenge.exceptions.EnderecoNaoEncontradoException;
import com.fiap.techchallenge.mappers.EnderecoMapper;
import com.fiap.techchallenge.repositories.EnderecoRepository;
import com.fiap.techchallenge.services.impl.EnderecoServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mapstruct.factory.Mappers;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fiap.techchallenge.enums.EstadoEnum.SP;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

@ExtendWith(SpringExtension.class)
class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService = new EnderecoServiceImpl();

    @Spy
    private EnderecoMapper enderecoMapper = Mappers.getMapper(EnderecoMapper.class);

    @Mock
    private EnderecoRepository enderecoRepository;

    private static final Long ID_EXISTENTE = 1L;

    @Test
    void deveObterEnderecoPorIdComSucesso() {
        Endereco endereco = new Endereco("Rua Teste", 123, "Bairro Teste", "Cidade Teste", SP);
        when(enderecoRepository.findById(ID_EXISTENTE)).thenReturn(Optional.of(endereco));

        EnderecoDTO enderecoDTO = enderecoService.getEnderecoById(ID_EXISTENTE);

        assertNotNull(enderecoDTO);
        assertEquals(endereco.getRua(), enderecoDTO.getRua());
        assertEquals(endereco.getNumero(), enderecoDTO.getNumero());
        assertEquals(endereco.getBairro(), enderecoDTO.getBairro());
        assertEquals(endereco.getCidade(), enderecoDTO.getCidade());
        assertEquals(endereco.getEstado(), enderecoDTO.getEstado());
    }

    @Test
    void deveLancarEnderecoNaoEncontradoExceptionAoObterEnderecoPorIdNaoEncontrado() {
        when(enderecoRepository.findById(ID_EXISTENTE)).thenReturn(Optional.empty());

        assertThrows(EnderecoNaoEncontradoException.class, () -> enderecoService.getEnderecoById(ID_EXISTENTE));
    }

    @Test
    void deveBuscarEnderecosPorFiltroComSucesso() {
        String rua = "Rua Teste";
        String bairro = "Bairro Teste";
        String cidade = "Cidade Teste";

        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(new Endereco(rua, 123, bairro, cidade, SP));

        when(enderecoRepository.buscarEnderecosPorFiltros(rua, bairro, cidade)).thenReturn(enderecos);

        List<EnderecoDTO> enderecoDTOs = enderecoService.buscarEnderecoPorFiltro(rua, bairro, cidade);

        assertNotNull(enderecoDTOs);
        assertEquals(1, enderecoDTOs.size());
        EnderecoDTO enderecoDTO = enderecoDTOs.get(0);
        assertEquals(rua, enderecoDTO.getRua());
        assertEquals(bairro, enderecoDTO.getBairro());
        assertEquals(cidade, enderecoDTO.getCidade());
    }

    @Test
    void deveLancarEnderecoNaoEncontradoExceptionAoBuscarEnderecosPorFiltroVazio() {
        String rua = "Rua Teste";
        String bairro = "Bairro Teste";
        String cidade = "Cidade Teste";

        when(enderecoRepository.buscarEnderecosPorFiltros(rua, bairro, cidade)).thenReturn(new ArrayList<>());

        assertThrows(EnderecoNaoEncontradoException.class, () -> enderecoService.buscarEnderecoPorFiltro(rua, bairro, cidade));
    }

    @Test
    void deveCadastrarEnderecoNaoExistenteComSucesso() {
        EnderecoDTO enderecoDTO = cadastrarEnderecoMock();
        Endereco enderecoSalvo = enderecoMapper.toEntity(enderecoDTO);

        when(enderecoRepository.buscarEnderecosPorFiltros(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString())
        ).thenReturn(new ArrayList<>());

        when(enderecoRepository.save(Mockito.any())).thenReturn(enderecoSalvo);

        EnderecoDTO enderecoCadastrado = enderecoService.cadastrarEndereco(enderecoDTO);

        assertNotNull(enderecoCadastrado);
        assertEquals(enderecoDTO.getRua(), enderecoCadastrado.getRua());
        assertEquals(enderecoDTO.getNumero(), enderecoCadastrado.getNumero());
        assertEquals(enderecoDTO.getBairro(), enderecoCadastrado.getBairro());
        assertEquals(enderecoDTO.getCidade(), enderecoCadastrado.getCidade());
        assertEquals(enderecoDTO.getEstado(), enderecoCadastrado.getEstado());
    }

    @Test
    void deveLancarEnderecoExistenteExceptionAoCadastrarEnderecoExistente() {
        EnderecoDTO enderecoDTOExistente = obterEnderecoDTO();

        List<Endereco> enderecosExistentes = singletonList(new Endereco());

        Mockito.when(enderecoRepository.findByAllFields(
                enderecoDTOExistente.getRua(),
                enderecoDTOExistente.getNumero(),
                enderecoDTOExistente.getBairro(),
                enderecoDTOExistente.getCidade(),
                enderecoDTOExistente.getEstado()
        )).thenReturn(enderecosExistentes);

        assertThrows(EnderecoExistenteException.class, () ->
                enderecoService.cadastrarEndereco(enderecoDTOExistente)
        );
    }

    @Test
    void deveAtualizarEndereco() {
        EnderecoDTO enderecoDTOAtualizado = obterEnderecoDTO();

        Endereco enderecoExistente = new Endereco();
        enderecoExistente.setId(ID_EXISTENTE);
        enderecoExistente.setRua("Rua Antiga");
        enderecoExistente.setNumero(123);

        when(enderecoRepository.findById(ID_EXISTENTE)).thenReturn(Optional.of(enderecoExistente));

        when(enderecoService.atualizarEndereco(ID_EXISTENTE, enderecoDTOAtualizado)).thenReturn(enderecoDTOAtualizado);

        EnderecoDTO enderecoAtualizado = enderecoService.atualizarEndereco(ID_EXISTENTE, enderecoDTOAtualizado);

        assertNotNull(enderecoAtualizado);
        assertEquals(enderecoDTOAtualizado.getRua(), enderecoAtualizado.getRua());
        assertEquals(enderecoDTOAtualizado.getNumero(), enderecoAtualizado.getNumero());
    }

    @Test
    void deveLancarEnderecoNaoEncontradoExceptionAoAtualizarEnderecoInexistente() {
        Long idInexistente = 999L;
        EnderecoDTO enderecoDTOAtualizado = new EnderecoDTO();
        enderecoDTOAtualizado.setRua("Nova Rua");
        enderecoDTOAtualizado.setNumero(456);

        when(enderecoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(EnderecoNaoEncontradoException.class, () ->
                enderecoService.atualizarEndereco(idInexistente, enderecoDTOAtualizado)
        );
    }

    @Test
    void deveLancarEnderecoCamposNaoPreenchidosExceptionAoAtualizarEnderecoSemCamposPreenchidos() {
        EnderecoDTO enderecoDTOSemCampos = new EnderecoDTO();

        assertThrows(EnderecoCamposNaoPreenchidosException.class, () ->
                enderecoService.atualizarEndereco(ID_EXISTENTE, enderecoDTOSemCampos)
        );
    }

    @Test
    void deveDeletarEnderecoComSucesso() {
        Endereco enderecoExistente = new Endereco();
        enderecoExistente.setId(ID_EXISTENTE);

        when(enderecoRepository.findById(ID_EXISTENTE)).thenReturn(Optional.of(enderecoExistente));

        enderecoService.deletarEndereco(ID_EXISTENTE);

        verify(enderecoRepository, times(1)).delete(enderecoExistente);
    }

    @Test
    void deveLancarEnderecoNaoEncontradoExceptionAoDeletarEnderecoInexistente() {
        Long idInexistente = 2L;

        when(enderecoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(EnderecoNaoEncontradoException.class, () -> enderecoService.deletarEndereco(idInexistente));

        verify(enderecoRepository, never()).delete(any(Endereco.class));
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

        when(enderecoRepository.findByAllFields(
                Mockito.anyString(),
                Mockito.anyInt(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(EstadoEnum.class)
        )).thenReturn(List.of());

        when(enderecoRepository.save(Mockito.any())).thenReturn(enderecoSalvo);

        return enderecoDTO;
    }
}