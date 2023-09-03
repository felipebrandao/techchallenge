package com.fiap.techchallenge.services;

import com.fiap.techchallenge.dtos.EletrodomesticoDTO;

import java.util.List;

public interface EletrodomesticoService {
    EletrodomesticoDTO cadastrarEndereco(EletrodomesticoDTO eletrodomesticoDTO);

    EletrodomesticoDTO atualizarEletrodomestico(Long id, EletrodomesticoDTO eletrodomesticoDTO);

    void deletarEletrodomestico(Long id);

    EletrodomesticoDTO getEletrodomesticoById(Long id);

    List<EletrodomesticoDTO> pesquisarEletrodomesticos(EletrodomesticoDTO dto);
}
