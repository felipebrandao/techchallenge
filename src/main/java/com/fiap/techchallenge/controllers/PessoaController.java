package com.fiap.techchallenge.controllers;

import com.fiap.techchallenge.dtos.PessoaDTO;
import com.fiap.techchallenge.services.PessoaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    private static final Logger LOGGER = LogManager.getLogger(PessoaController.class);
    @PostMapping
    public ResponseEntity cadastro(@Valid @RequestBody PessoaDTO pessoaDTO,
                                   HttpServletRequest request, HttpServletResponse response){
        LOGGER.info("Inicio da requisição");
        PessoaDTO pessoaGravada =pessoaService.cadastrarPessoa(pessoaDTO);
        LOGGER.info("Fim da requisição");
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaGravada);
    }
}
