package com.fiap.techchallenge.services;

import com.fiap.techchallenge.dtos.EnderecoDTO;

import java.util.List;

public interface EnderecoService {

    List<EnderecoDTO> obterEnderecos();

    EnderecoDTO cadastrarEndereco(EnderecoDTO enderecoDTO);

    void deletarEndereco(Long id);
}
