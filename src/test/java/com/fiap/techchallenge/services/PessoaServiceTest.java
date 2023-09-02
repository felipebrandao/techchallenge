package com.fiap.techchallenge.services;

import com.fiap.techchallenge.dtos.PessoaDTO;
import com.fiap.techchallenge.entities.Pessoa;
import com.fiap.techchallenge.enums.SexoEnum;
import com.fiap.techchallenge.exceptions.PessoaExisteException;
import com.fiap.techchallenge.exceptions.PessoaNaoEncontradaException;
import com.fiap.techchallenge.mappers.PessoaMapper;
import com.fiap.techchallenge.repositories.PessoaRepository;
import com.fiap.techchallenge.services.impl.ConsumoServiceImpl;
import com.fiap.techchallenge.services.impl.PessoaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PessoaServiceTest {

    private PessoaService pessoaService;

    @Spy
    private PessoaMapper pessoaMapper = Mappers.getMapper(PessoaMapper.class);

    @Mock
    private PessoaRepository pessoaRepository;

    private static final String CPF_EXISTENTE = "1";
    private static final Long ID_EXISTENTE = 1L;
    private static final String NOME_ANTIGO = "Antigo Nome";

    @BeforeEach
    void setup() {
        pessoaService = new PessoaServiceImpl(pessoaRepository, pessoaMapper);
    }

    private PessoaDTO obterPessoaDTO() {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("Teste");
        pessoaDTO.setCpf(CPF_EXISTENTE);
        pessoaDTO.setSexo(SexoEnum.FEMININO);
        pessoaDTO.setDataDeNascimento(LocalDate.now());
        return pessoaDTO;
    }

    @Test
    void testCadastroPessoaNaoExiste() {
        when(pessoaRepository.existsByCpf(Mockito.anyString())).thenReturn(false);

        PessoaDTO pessoaDTO = obterPessoaDTO();
        Pessoa pessoaSalva = pessoaMapper.toEntity(pessoaDTO);
        when(pessoaRepository.save(Mockito.any())).thenReturn(pessoaSalva);

        PessoaDTO pessoaCadastrada = pessoaService.cadastrarPessoa(obterPessoaDTO());

        verify(pessoaRepository, times(1)).existsByCpf(CPF_EXISTENTE);
        verify(pessoaRepository, times(1)).save(Mockito.any());

        assertNotNull(pessoaCadastrada);
        assertEquals(pessoaDTO.getNome(), pessoaCadastrada.getNome());
        assertEquals(pessoaDTO.getCpf(), pessoaCadastrada.getCpf());
        assertEquals(pessoaDTO.getSexo(), pessoaCadastrada.getSexo());
        assertEquals(pessoaDTO.getDataDeNascimento(), pessoaCadastrada.getDataDeNascimento());
    }

    @Test
    void testCadastroPessoaJaExiste() {
        when(pessoaRepository.existsByCpf(Mockito.anyString())).thenReturn(true);

        PessoaExisteException pessoaExisteException = assertThrows(
                PessoaExisteException.class,
                () -> pessoaService.cadastrarPessoa(obterPessoaDTO())
        );

        assertEquals("Pessoa já cadastrada com este CPF.", pessoaExisteException.getMessage());

        verify(pessoaRepository, times(1)).existsByCpf(CPF_EXISTENTE);
        verify(pessoaRepository, never()).save(Mockito.any());
    }

    @Test
    void testAtualizarPessoaPessoaNaoEncontrada() {
        when(pessoaRepository.findById(ID_EXISTENTE)).thenReturn(Optional.empty());

        PessoaNaoEncontradaException pessoaNaoEncontradaException = assertThrows(
                PessoaNaoEncontradaException.class,
                () -> pessoaService.atualizarPessoa(ID_EXISTENTE, obterPessoaDTO())
        );

        assertEquals(
                "Pessoa com este id: " + ID_EXISTENTE + " e CPF: " + CPF_EXISTENTE + " não encontrada.",
                pessoaNaoEncontradaException.getMessage()
        );
    }

    @Test
    void testAtualizarPessoa() {
        Long idExistente = 1L;
        PessoaDTO pessoaDTO = obterPessoaDTO();

        Pessoa pessoaExistente = new Pessoa(NOME_ANTIGO, LocalDate.now(), SexoEnum.MASCULINO, CPF_EXISTENTE);

        when(pessoaRepository.findByIdAndCPF(Mockito.any(), Mockito.any())).thenReturn(pessoaExistente);
        when(pessoaRepository.save(Mockito.any())).thenReturn(pessoaExistente);

        PessoaDTO pessoaAtualizada = pessoaService.atualizarPessoa(idExistente, pessoaDTO);

        assertNotNull(pessoaAtualizada);
        assertEquals(pessoaDTO.getNome(), pessoaAtualizada.getNome());
        assertEquals(pessoaDTO.getCpf(), pessoaAtualizada.getCpf());
    }

    @Test
    void testDeletarPessoaPessoaNaoEncontrada() {
        when(pessoaRepository.findById(ID_EXISTENTE)).thenReturn(Optional.empty());

        PessoaNaoEncontradaException pessoaNaoEncontradaException = assertThrows(
                PessoaNaoEncontradaException.class,
                () -> pessoaService.deletarPessoa(ID_EXISTENTE)
        );

        assertEquals(
                "Pessoa com ID " + ID_EXISTENTE + " não encontrada.",
                pessoaNaoEncontradaException.getMessage()
        );
    }

    @Test
    void testDeletarPessoa() {
        when(pessoaRepository.findById(ID_EXISTENTE)).thenReturn(
                Optional.of(new Pessoa())
        );

        assertDoesNotThrow(
                () -> pessoaService.deletarPessoa(ID_EXISTENTE),
                "Não deve lançar exceção ao deletar pessoa existente"
        );
    }

    @Test
    void testGetPessoaByIdPessoaNaoEncontrada() {
        when(pessoaRepository.findById(ID_EXISTENTE)).thenReturn(Optional.empty());

        PessoaNaoEncontradaException pessoaNaoEncontradaException = assertThrows(
                PessoaNaoEncontradaException.class,
                () -> pessoaService.getPessoaById(ID_EXISTENTE)
        );

        assertEquals(
                "Pessoa com ID " + ID_EXISTENTE + " não encontrada.",
                pessoaNaoEncontradaException.getMessage()
        );
    }

    @Test
    void testGetPessoaById() {
        PessoaDTO pessoaDTO = obterPessoaDTO();
        Pessoa pessoaExistente = pessoaMapper.toEntity(pessoaDTO);

        when(pessoaRepository.findById(ID_EXISTENTE)).thenReturn(Optional.of(pessoaExistente));

        PessoaDTO pessoaEncontrada = pessoaService.getPessoaById(ID_EXISTENTE);

        assertNotNull(pessoaEncontrada);
        assertEquals(pessoaDTO.getNome(), pessoaEncontrada.getNome());
        assertEquals(pessoaDTO.getCpf(), pessoaEncontrada.getCpf());
    }

    @Test
    void testBuscarPessoasPorNomeECPF() {
        PessoaDTO pessoaDTO1 = obterPessoaDTO();
        pessoaDTO1.setNome("Maria");
        Pessoa entity1 = pessoaMapper.toEntity(pessoaDTO1);

        PessoaDTO pessoaDTO2 = obterPessoaDTO();
        pessoaDTO2.setNome("João");
        Pessoa entity2 = pessoaMapper.toEntity(pessoaDTO2);


        when(pessoaRepository.findByNomeAndCpf("Maria", null)).thenReturn(List.of(entity1));
        when(pessoaRepository.findByNomeAndCpf("Maria", "54321678901")).thenReturn(List.of(entity1));
        when(pessoaRepository.findByNomeAndCpf("João", "12345678901")).thenReturn(List.of());

        List<PessoaDTO> pessoasEncontradas = pessoaService.buscarPessoasPorNomeECPF("Maria", null);
        assertEquals(1, pessoasEncontradas.size());
        assertEquals("Maria", pessoasEncontradas.get(0).getNome());

        pessoasEncontradas = pessoaService.buscarPessoasPorNomeECPF("Maria", "54321678901");
        assertEquals(1, pessoasEncontradas.size());
        assertEquals("Maria", pessoasEncontradas.get(0).getNome());

        pessoasEncontradas = pessoaService.buscarPessoasPorNomeECPF("João", "12345678901");
        assertEquals(0, pessoasEncontradas.size());
    }

}
