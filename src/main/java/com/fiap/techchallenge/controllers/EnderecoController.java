package com.fiap.techchallenge.controllers;

import com.fiap.techchallenge.dtos.EnderecoDTO;
import com.fiap.techchallenge.services.EnderecoService;
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
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    private static final Logger LOGGER = LogManager.getLogger(EnderecoController.class);

    @PostMapping
    public ResponseEntity<EnderecoDTO> cadastro(@Valid @RequestBody EnderecoDTO enderecoDTO, HttpServletRequest request, HttpServletResponse response){
        LOGGER.info("Inicio da requisição - EnderecoController - cadastro");
        EnderecoDTO enderecoGravado = enderecoService.cadastrarEndereco(enderecoDTO);
        LOGGER.info("Fim da requisição - EnderecoController - cadastro");
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoGravado);
    }
}
