package com.fiap.techchallenge.services.impl;

import com.fiap.techchallenge.dtos.PessoaDTO;
import com.fiap.techchallenge.entities.Pessoa;
import com.fiap.techchallenge.exceptions.PessoaExisteException;
import com.fiap.techchallenge.mappers.PessoaMapper;
import com.fiap.techchallenge.repositories.PessoaRepository;
import com.fiap.techchallenge.services.PessoaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PessoaServiceImpl implements PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaMapper pessoaMapper;

    private static final Logger LOGGER = LogManager.getLogger(PessoaServiceImpl.class);
    @Override
    public PessoaDTO cadastrarPessoa(PessoaDTO pessoaDTO) {
        LOGGER.info("Inicio do metódo - PessoaServiceImpl - cadastrarPessoa");
        isExistePessoa(pessoaDTO.getCpf());
        Pessoa pessoa = pessoaRepository.save(pessoaMapper.toEntity(pessoaDTO));
        LOGGER.info("Fim do metódo - PessoaServiceImpl - cadastrarPessoa");
        return pessoaMapper.toDTO(pessoa);
    }

    private void isExistePessoa(String cpf) {
        LOGGER.info("Inicio do metódo - PessoaServiceImpl - isExistePessoa");
        boolean existePessoa = pessoaRepository.existsByCpf(cpf);
        if (existePessoa) {
            throw new PessoaExisteException("Pessoa já cadastrada com este CPF");
        }
        LOGGER.info("Fim do metódo - PessoaServiceImpl - isExistePessoa");
    }
}
