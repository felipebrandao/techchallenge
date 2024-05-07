package com.fiap.techchallenge.morador.services;

import com.fiap.techchallenge.morador.dtos.MoradorDTO;

import java.util.List;

public interface MoradorService {
    MoradorDTO criarMorador(MoradorDTO moradorDTO);

    MoradorDTO encontrarMoradorPorId(Long id);

    void deletarMorador(Long id);

    List<MoradorDTO> pesquisarMoradores(Long idPessoa, Long idEndereco);
}
