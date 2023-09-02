package com.fiap.techchallenge.services;

import com.fiap.techchallenge.dtos.MoradorDTO;

import java.util.List;

public interface MoradorService {
    MoradorDTO criarMorador(MoradorDTO moradorDTO);

    MoradorDTO encontrarMoradorPorId(Long id);

    void deletarMorador(Long id);

    List<MoradorDTO> pesquisarMoradores(Long idPessoa, Long idEndereco);
}
