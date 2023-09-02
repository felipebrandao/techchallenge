package com.fiap.techchallenge.services;

import com.fiap.techchallenge.dtos.PessoaDTO;
import com.fiap.techchallenge.exceptions.PessoaExisteException;

import java.util.List;

public interface PessoaService {
    PessoaDTO cadastrarPessoa(PessoaDTO pessoaDTO);

    PessoaDTO atualizarPessoa(Long id, PessoaDTO pessoaDTO);

    void deletarPessoa(Long id);

    PessoaDTO getPessoaById(Long id);

    List<PessoaDTO> buscarPessoasPorNomeECPF(String nome, String cpf);
}
