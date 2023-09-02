package com.fiap.techchallenge.services;

import com.fiap.techchallenge.dtos.EnderecoDTO;

import java.util.List;

public interface EnderecoService {

    List<EnderecoDTO> obterEnderecos();

    List<EnderecoDTO> buscarEnderecosPorFiltros(String rua, String bairro, String cidade);

    EnderecoDTO cadastrarEndereco(EnderecoDTO enderecoDTO);

    void deletarEndereco(Long id);
}
