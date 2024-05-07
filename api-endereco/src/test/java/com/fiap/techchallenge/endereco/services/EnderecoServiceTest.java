package com.fiap.techchallenge.endereco.endereco.services;

import com.fiap.techchallenge.endereco.dtos.EnderecoDTO;
import com.fiap.techchallenge.endereco.entities.Endereco;
import com.fiap.techchallenge.endereco.enums.EstadoEnum;
import com.fiap.techchallenge.endereco.exceptions.EnderecoCamposNaoPreenchidosException;
import com.fiap.techchallenge.endereco.exceptions.EnderecoExistenteException;
import com.fiap.techchallenge.endereco.exceptions.EnderecoNaoEncontradoException;
import com.fiap.techchallenge.endereco.mappers.EnderecoMapper;
import com.fiap.techchallenge.endereco.repositories.EnderecoRepository;
import com.fiap.techchallenge.endereco.services.EnderecoService;
import com.fiap.techchallenge.endereco.services.impl.EnderecoServiceImpl;
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

import static com.fiap.techchallenge.endereco.enums.EstadoEnum.SP;
import static org.assertj.core.api.Assertions.assertThat;
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
    void testObterEnderecoPorIdComSucesso() {
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
    void testLancarEnderecoNaoEncontradoExceptionAoObterEnderecoPorIdNaoEncontrado() {
        when(enderecoRepository.findById(ID_EXISTENTE)).thenReturn(Optional.empty());

        assertThrows(EnderecoNaoEncontradoException.class, () -> enderecoService.getEnderecoById(ID_EXISTENTE));
    }

    @Test
    void testBuscarEnderecosPorFiltroComSucesso() {
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
    void testLancarEnderecoNaoEncontradoExceptionAoBuscarEnderecosPorFiltroVazio() {
        String rua = "Rua Teste";
        String bairro = "Bairro Teste";
        String cidade = "Cidade Teste";

        when(enderecoRepository.buscarEnderecosPorFiltros(rua, bairro, cidade)).thenReturn(new ArrayList<>());

        assertThrows(EnderecoNaoEncontradoException.class, () -> enderecoService.buscarEnderecoPorFiltro(rua, bairro, cidade));
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
    void testLancarEnderecoExistenteExceptionAoCadastrarEnderecoExistente() {
        EnderecoDTO enderecoDTOExistente = obterEnderecoDTO();

        when(enderecoRepository.isExisteEnderecoCadastrado(
                enderecoDTOExistente.getRua(),
                enderecoDTOExistente.getNumero(),
                enderecoDTOExistente.getBairro(),
                enderecoDTOExistente.getCidade(),
                enderecoDTOExistente.getEstado()
        )).thenReturn(true);

        assertThrows(EnderecoExistenteException.class, () ->
                enderecoService.cadastrarEndereco(enderecoDTOExistente)
        );
    }

    @Test
    void testAtualizarEndereco() {
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
    void testLancarEnderecoNaoEncontradoExceptionAoAtualizarEnderecoInexistente() {
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
    void testDeveLancarEnderecoCamposNaoPreenchidosExceptionAoAtualizarEnderecoSemCamposPreenchidos() {
        EnderecoDTO enderecoDTOSemCampos = new EnderecoDTO();

        assertThrows(EnderecoCamposNaoPreenchidosException.class, () ->
                enderecoService.atualizarEndereco(ID_EXISTENTE, enderecoDTOSemCampos)
        );
    }

    @Test
    void testDeletarEnderecoComSucesso() {
        Endereco enderecoExistente = new Endereco();
        enderecoExistente.setId(ID_EXISTENTE);

        when(enderecoRepository.findById(ID_EXISTENTE)).thenReturn(Optional.of(enderecoExistente));

        enderecoService.deletarEndereco(ID_EXISTENTE);

        verify(enderecoRepository, times(1)).delete(enderecoExistente);
    }

    @Test
    void testLancarEnderecoNaoEncontradoExceptionAoDeletarEnderecoInexistente() {
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

    private void cadastrarEnderecoMock() {
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

    }
}
