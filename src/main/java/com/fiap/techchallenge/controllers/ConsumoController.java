package com.fiap.techchallenge.controllers;

import com.fiap.techchallenge.dtos.ConsumoDTO;
import com.fiap.techchallenge.services.ConsumoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/consumo")
public class ConsumoController {

    @Autowired
    private ConsumoService consumoService;

    @PostMapping
    public ResponseEntity<ConsumoDTO> registrarConsumo(@Valid @RequestBody ConsumoDTO consumoDTO) {
        log.info("Iniciando o registro do consumo.");
        ConsumoDTO novoConsumo = consumoService.registrarConsumo(consumoDTO);
        log.info("Registrado consumo com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(novoConsumo);
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<ConsumoDTO>> pesquisarConsumos(
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim,
            @RequestParam(required = false) Long idPessoa,
            @RequestParam(required = false) Long idEndereco,
            @RequestParam(required = false) Long idEletrodomestico) {
        log.info("Iniciando a pesquisa de consumos.");

        List<ConsumoDTO> consumos = consumoService.pesquisarConsumos(dataInicio, dataFim, idPessoa, idEndereco, idEletrodomestico);

        log.info("Pesquisa de consumos concluída.");
        return ResponseEntity.status(HttpStatus.OK).body(consumos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumoDTO> encontrarConsumoPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(consumoService.encontrarConsumoPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConsumo(@PathVariable Long id) {
        log.info("Iniciando a exclusão do consumo com ID: {}.", id);
        consumoService.deletarConsumo(id);
        log.info("Consumo com ID: {} excluída com sucesso.", id);
        return ResponseEntity.noContent().build();
    }
}
