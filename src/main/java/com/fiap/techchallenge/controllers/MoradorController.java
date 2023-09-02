package com.fiap.techchallenge.controllers;

import com.fiap.techchallenge.dtos.MoradorDTO;
import com.fiap.techchallenge.services.MoradorService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/morador")
public class MoradorController {

    @Autowired
    private MoradorService moradorService;

    @PostMapping
    public ResponseEntity<MoradorDTO> registrarMorador(@Valid @RequestBody MoradorDTO moradorDTO) {
        log.info("Iniciando o registro do morador.");
        MoradorDTO novoMorador = moradorService.criarMorador(moradorDTO);
        log.info("Registrado morador com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMorador);
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<MoradorDTO>> pesquisarMoradores(
            @RequestParam(required = false) Long idPessoa,
            @RequestParam(required = false) Long idEndereco) {
        log.info("Iniciando a pesquisa de moradores.");

        List<MoradorDTO> moradores = moradorService.pesquisarMoradores(idPessoa, idEndereco);

        log.info("Pesquisa de moradores concluída.");
        return ResponseEntity.status(HttpStatus.OK).body(moradores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoradorDTO> encontrarMoradorPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(moradorService.encontrarMoradorPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMorador(@PathVariable Long id) {
        log.info("Iniciando a exclusão do morador com ID: {}.", id);
        moradorService.deletarMorador(id);
        log.info("Morador com ID: {} excluída com sucesso.", id);
        return ResponseEntity.noContent().build();
    }
}
