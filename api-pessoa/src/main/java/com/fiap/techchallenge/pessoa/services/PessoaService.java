package com.fiap.techchallenge.pessoa.services;

import com.fiap.techchallenge.pessoa.dtos.PessoaDTO;

import java.util.List;

public interface PessoaService {
    PessoaDTO cadastrarPessoa(PessoaDTO pessoaDTO);

    PessoaDTO atualizarPessoa(Long id, PessoaDTO pessoaDTO);

    void deletarPessoa(Long id);

    PessoaDTO getPessoaById(Long id);

    List<PessoaDTO> buscarPessoasPorNomeECPF(String nome, String cpf);
}
