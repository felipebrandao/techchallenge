package com.fiap.techchallenge.controllers;

import com.fiap.techchallenge.dtos.EletrodomesticoDTO;
import com.fiap.techchallenge.services.EletrodomesticoService;
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
@RequestMapping("/eletrodomestico")
public class EletrodomesticoController {

    @Autowired
    private EletrodomesticoService eletrodomesticoService;
    private static final Logger LOGGER = LogManager.getLogger(EletrodomesticoController.class);
    @PostMapping
    public ResponseEntity cadastro(@Valid @RequestBody EletrodomesticoDTO eletrodomesticoDTO, HttpServletRequest request, HttpServletResponse response){
        LOGGER.info("Inicio da requisição");
        EletrodomesticoDTO enderecoGravado = eletrodomesticoService.cadastrarEndereco(eletrodomesticoDTO);
        LOGGER.info("Fim da requisição");
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoGravado);
    }
}
