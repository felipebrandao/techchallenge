package com.fiap.techchallenge.morador.services;

import com.fiap.techchallenge.morador.dtos.MoradorDTO;
import com.fiap.techchallenge.morador.entities.Morador;
import com.fiap.techchallenge.morador.exceptions.MoradorCadastroNoEnderecoException;
import com.fiap.techchallenge.morador.exceptions.MoradorNaoEncontradaException;
import com.fiap.techchallenge.morador.feign.EnderecoFeignClient;
import com.fiap.techchallenge.morador.feign.PessoaFeignClient;
import com.fiap.techchallenge.morador.feign.dtos.PessoaDTO;
import com.fiap.techchallenge.morador.mappers.MoradorMapper;
import com.fiap.techchallenge.morador.repositories.MoradorRepository;
import com.fiap.techchallenge.morador.services.impl.MoradorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class MoradorServiceImplTest {

    private MoradorServiceImpl moradorService;

    @Mock
    private MoradorRepository moradorRepository;

    @Mock
    private MoradorMapper moradorMapper;

    @Mock
    private PessoaFeignClient pessoaFeignClient;

    @Mock
    private EnderecoFeignClient enderecoFeignClient;

    @BeforeEach
    public void setUp() {
        moradorService = new MoradorServiceImpl(moradorRepository, moradorMapper, pessoaFeignClient, enderecoFeignClient);
    }

    @Test
    public void testCriarMorador() {
        // Arrange
        MoradorDTO moradorDTO = new MoradorDTO();
        moradorDTO.setIdPessoa(1L);
        moradorDTO.setIdEndereco(2L);
        moradorDTO.setParentesco("Parentesco do Morador");

        Morador morador = new Morador();
        morador.setId(1L);
        morador.setIdPessoa(1L);
        morador.setIdEndereco(2L);
        morador.setParentesco("Parentesco do Morador");

        when(pessoaFeignClient.getPessoaById(1L)).thenReturn(new PessoaDTO());
        when(moradorRepository.isExistePessoaCadastradaNoEndereco(2L, 1L)).thenReturn(false);
        when(moradorMapper.toEntity(moradorDTO)).thenReturn(morador);
        when(moradorRepository.save(morador)).thenReturn(morador);
        when(moradorMapper.toDTO(morador)).thenReturn(moradorDTO);

        MoradorDTO result = moradorService.criarMorador(moradorDTO);

        assertNotNull(result);
        assertEquals(moradorDTO.getIdPessoa(), result.getIdPessoa());
        assertEquals(moradorDTO.getIdEndereco(), result.getIdEndereco());
        assertEquals(moradorDTO.getParentesco(), result.getParentesco());
    }

    @Test
    public void testCriarMoradorCadastroNoEndereco() {
        MoradorDTO moradorDTO = new MoradorDTO();
        moradorDTO.setIdPessoa(1L);
        moradorDTO.setIdEndereco(2L);
        moradorDTO.setParentesco("Parentesco do Morador");

        when(pessoaFeignClient.getPessoaById(1L)).thenReturn(new PessoaDTO());
        when(moradorRepository.isExistePessoaCadastradaNoEndereco(2L, 1L)).thenReturn(true);

        assertThrows(MoradorCadastroNoEnderecoException.class, () -> {
            moradorService.criarMorador(moradorDTO);
        });
    }

    @Test
    public void testCriarMoradorNaoEncontrada() {
        MoradorDTO moradorDTO = new MoradorDTO();
        moradorDTO.setIdPessoa(1L);
        moradorDTO.setIdEndereco(2L);
        moradorDTO.setParentesco("Parentesco do Morador");

        when(pessoaFeignClient.getPessoaById(1L)).thenThrow(MoradorNaoEncontradaException.class);

        assertThrows(MoradorNaoEncontradaException.class, () -> {
            moradorService.criarMorador(moradorDTO);
        });
    }

    @Test
    public void testEncontrarMoradorPorId() {
        Long moradorId = 1L;
        Morador morador = new Morador();
        morador.setId(moradorId);

        MoradorDTO moradorDTO = new MoradorDTO();
        moradorDTO.setId(moradorId);

        when(moradorRepository.findById(moradorId)).thenReturn(Optional.of(morador));
        when(moradorMapper.toDTO(morador)).thenReturn(moradorDTO);

        MoradorDTO result = moradorService.encontrarMoradorPorId(moradorId);

        assertNotNull(result);
        assertEquals(moradorId, result.getId());
    }

    @Test
    public void testEncontrarMoradorPorIdNaoEncontrado() {
        Long moradorId = 1L;

        when(moradorRepository.findById(moradorId)).thenReturn(Optional.empty());

        assertThrows(MoradorNaoEncontradaException.class, () -> {
            moradorService.encontrarMoradorPorId(moradorId);
        });
    }

    @Test
    public void testDeletarMorador() {
        Long moradorId = 1L;
        Morador morador = new Morador();
        morador.setId(moradorId);

        when(moradorRepository.findById(moradorId)).thenReturn(Optional.of(morador));

        moradorService.deletarMorador(moradorId);

        verify(moradorRepository, times(1)).delete(morador);
    }

    @Test
    public void testDeletarMoradorNaoEncontrado() {
        Long moradorId = 1L;

        when(moradorRepository.findById(moradorId)).thenReturn(Optional.empty());

        assertThrows(MoradorNaoEncontradaException.class, () -> {
            moradorService.deletarMorador(moradorId);
        });
    }

    @Test
    public void testPesquisarMoradores() {
        Long idPessoa = 1L;
        Long idEndereco = 2L;

        Morador morador1 = new Morador();
        Morador morador2 = new Morador();

        when(moradorRepository.pesquisarMoradores(idPessoa, idEndereco))
                .thenReturn(Arrays.asList(morador1, morador2));
        when(moradorMapper.toDTOList(Arrays.asList(morador1, morador2)))
                .thenReturn(Arrays.asList(new MoradorDTO(), new MoradorDTO()));

        List<MoradorDTO> result = moradorService.pesquisarMoradores(idPessoa, idEndereco);

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
