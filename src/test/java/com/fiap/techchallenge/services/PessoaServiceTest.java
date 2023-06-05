package com.fiap.techchallenge.services;

import com.fiap.techchallenge.dtos.PessoaDTO;
import com.fiap.techchallenge.enums.SexoEnum;
import com.fiap.techchallenge.exceptions.PessoaExisteException;
import com.fiap.techchallenge.repositories.PessoaRepository;
import com.fiap.techchallenge.services.impl.PessoaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService = new PessoaServiceImpl();

    @Mock
    private PessoaRepository pessoaRepository;

    private PessoaDTO obterPessoaDTO() {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("Felipe");
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

}