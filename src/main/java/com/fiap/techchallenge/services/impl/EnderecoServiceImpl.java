package com.fiap.techchallenge.services.impl;

import com.fiap.techchallenge.dtos.EnderecoDTO;
import com.fiap.techchallenge.entities.Endereco;
import com.fiap.techchallenge.repositories.EnderecoRepository;
import com.fiap.techchallenge.services.EnderecoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    private static final Logger LOGGER = LogManager.getLogger(EnderecoServiceImpl.class);
    @Override
    public EnderecoDTO cadastrarEndereco(EnderecoDTO enderecoDTO) {
        LOGGER.info("Inicio do metódo");
        Endereco endereco = enderecoRepository.save(enderecoDTO.toEntity());
        LOGGER.info("Fim do metódo");
        return new EnderecoDTO(endereco);
    }
}
