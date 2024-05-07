package com.fiap.techchallenge.eletrodomestico.services;

import com.fiap.techchallenge.eletrodomestico.dtos.EletrodomesticoDTO;

import java.util.List;

public interface EletrodomesticoService {
    EletrodomesticoDTO cadastrarEletrodomestico(EletrodomesticoDTO eletrodomesticoDTO);

    EletrodomesticoDTO atualizarEletrodomestico(Long id, EletrodomesticoDTO eletrodomesticoDTO);

    void deletarEletrodomestico(Long id);

    EletrodomesticoDTO getEletrodomesticoById(Long id);

    List<EletrodomesticoDTO> pesquisarEletrodomesticos(
            String nome,
            String modelo,
            Double potencia);
}
