package com.fiap.techchallenge.services.impl;

import com.fiap.techchallenge.dtos.EletrodomesticoDTO;
import com.fiap.techchallenge.entities.Eletrodomestico;
import com.fiap.techchallenge.repositories.EletrodomesticoRepository;
import com.fiap.techchallenge.services.EletrodomesticoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EletrodomesticoServiceImpl implements EletrodomesticoService {

    @Autowired
    private EletrodomesticoRepository eletrodomesticoRepository;
    private static final Logger LOGGER = LogManager.getLogger(EletrodomesticoServiceImpl.class);
    @Override
    public EletrodomesticoDTO cadastrarEndereco(EletrodomesticoDTO eletrodomesticoDTO) {
        LOGGER.info("Inicio do metódo");
        Eletrodomestico eletrodomestico = eletrodomesticoRepository.save(eletrodomesticoDTO.toEntity());
        LOGGER.info("Fim da requisição");
        return new EletrodomesticoDTO(eletrodomestico);
    }
}
