package com.fiap.techchallenge.services;

import com.fiap.techchallenge.dtos.PessoaDTO;
import com.fiap.techchallenge.exceptions.PessoaExisteException;

public interface PessoaService {
    PessoaDTO cadastrarPessoa(PessoaDTO pessoaDTO);
}
