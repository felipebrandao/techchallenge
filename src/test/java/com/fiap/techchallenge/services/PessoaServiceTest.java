package com.fiap.techchallenge.services;

import com.fiap.techchallenge.dtos.PessoaDTO;
import com.fiap.techchallenge.entities.Pessoa;
import com.fiap.techchallenge.enums.SexoEnum;
import com.fiap.techchallenge.exceptions.PessoaExisteException;
import com.fiap.techchallenge.mappers.PessoaMapper;
import com.fiap.techchallenge.repositories.PessoaRepository;
import com.fiap.techchallenge.services.impl.PessoaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService = new PessoaServiceImpl();

    @Spy
    private PessoaMapper pessoaMapper = Mappers.getMapper(PessoaMapper.class);

    @Mock
    private PessoaRepository pessoaRepository;

    private PessoaDTO obterPessoaDTO() {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("Teste");
        pessoaDTO.setCpf("1");
        pessoaDTO.setSexo(SexoEnum.FEMININO);
        pessoaDTO.setDataDeNascimento(LocalDate.now());
        return pessoaDTO;
    }

    @Test
    public void cadastroPessoaJaExiste() {
        when(pessoaRepository.existsByCpf(Mockito.anyString())).thenReturn(true);

        PessoaExisteException pessoaExisteException = assertThrows(PessoaExisteException.class, () -> {
            pessoaService.cadastrarPessoa(obterPessoaDTO());
        });

        assertEquals("Pessoa já cadastrada com este CPF", pessoaExisteException.getMessage());
    }

    @Test
    void cadastrarPessoa() {
        when(pessoaRepository.save(Mockito.any())).thenReturn(new Pessoa("Teste", LocalDate.now(), SexoEnum.FEMININO, "1"));

        PessoaDTO pessoaDTO = pessoaService.cadastrarPessoa(obterPessoaDTO());

        assertNotNull(pessoaDTO);
    }
}