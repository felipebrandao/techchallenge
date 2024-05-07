package com.fiap.techchallenge.endereco.services;

import com.fiap.techchallenge.endereco.dtos.EnderecoDTO;

import java.util.List;

public interface EnderecoService {

    EnderecoDTO getEnderecoById(Long id);

    List<EnderecoDTO> buscarEnderecoPorFiltro(String rua, String bairro, String cidade);

    EnderecoDTO cadastrarEndereco(EnderecoDTO enderecoDTO);

    EnderecoDTO atualizarEndereco(Long id, EnderecoDTO enderecoDTO);

    void deletarEndereco(Long id);
}
