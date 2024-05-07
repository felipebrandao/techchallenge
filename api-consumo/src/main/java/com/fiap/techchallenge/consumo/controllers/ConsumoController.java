package com.fiap.techchallenge.consumo.controllers;

import com.fiap.techchallenge.consumo.dtos.ConsumoDTO;
import com.fiap.techchallenge.consumo.services.ConsumoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/consumo")
@Tag(name = "Consumo", description = "Endpoints para gerenciamento de consumos")
public class ConsumoController {

    @Autowired
    private ConsumoService consumoService;

    @PostMapping
    @Operation(summary = "Registrar consumo", description = "Registra um novo consumo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consumo registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<ConsumoDTO> registrarConsumo(@Valid @RequestBody ConsumoDTO consumoDTO) {
        log.info("Iniciando o registro do consumo.");
        ConsumoDTO novoConsumo = consumoService.registrarConsumo(consumoDTO);
        log.info("Registrado consumo com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(novoConsumo);
    }

    @GetMapping("/pesquisar")
    @Operation(summary = "Pesquisar consumos", description = "Pesquisa consumos a partir de filtros opcionais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consumos encontrados"),
            @ApiResponse(responseCode = "404", description = "Consumos não encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<ConsumoDTO>> pesquisarConsumos(
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim,
            @RequestParam(required = false) Long idPessoa,
            @RequestParam(required = false) Long idEletrodomestico) {
        log.info("Iniciando a pesquisa de consumos.");

        List<ConsumoDTO> consumos = consumoService.pesquisarConsumos(dataInicio, dataFim, idPessoa, idEletrodomestico);

        log.info("Pesquisa de consumos concluída.");
        return ResponseEntity.status(HttpStatus.OK).body(consumos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Encontrar consumo por ID", description = "Encontra um consumo a partir do seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consumo encontrado"),
            @ApiResponse(responseCode = "404", description = "Consumo não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<ConsumoDTO> encontrarConsumoPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(consumoService.encontrarConsumoPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar consumo", description = "Deleta um consumo a partir do seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consumo deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consumo não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletarConsumo(@PathVariable Long id) {
        log.info("Iniciando a exclusão do consumo com ID: {}.", id);
        consumoService.deletarConsumo(id);
        log.info("Consumo com ID: {} excluída com sucesso.", id);
        return ResponseEntity.noContent().build();
    }
}
