package com.fiap.techchallenge.morador.controllers;

import com.fiap.techchallenge.morador.dtos.MoradorDTO;
import com.fiap.techchallenge.morador.services.MoradorService;
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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/morador")
@Tag(name = "Morador", description = "Endpoints para gerenciamento de moradores")
public class MoradorController {

    @Autowired
    private MoradorService moradorService;

    @PostMapping
    @Operation(summary = "Registrar morador", description = "Registra um novo morador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Morador registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<MoradorDTO> registrarMorador(@Valid @RequestBody MoradorDTO moradorDTO) {
        log.info("Iniciando o registro do morador.");
        MoradorDTO novoMorador = moradorService.criarMorador(moradorDTO);
        log.info("Registrado morador com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMorador);
    }

    @GetMapping("/pesquisar")
    @Operation(summary = "Pesquisar moradores", description = "Pesquisa moradores a partir de filtros opcionais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Moradores encontrados"),
            @ApiResponse(responseCode = "404", description = "Moradores não encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<MoradorDTO>> pesquisarMoradores(
            @RequestParam(required = false) Long idPessoa,
            @RequestParam(required = false) Long idEndereco) {
        log.info("Iniciando a pesquisa de moradores.");

        List<MoradorDTO> moradores = moradorService.pesquisarMoradores(idPessoa, idEndereco);

        log.info("Pesquisa de moradores concluída.");
        return ResponseEntity.status(HttpStatus.OK).body(moradores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter morador por ID", description = "Obtém um morador a partir do seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Morador encontrado"),
            @ApiResponse(responseCode = "404", description = "Morador não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<MoradorDTO> encontrarMoradorPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(moradorService.encontrarMoradorPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar morador", description = "Deleta um morador a partir do seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Morador deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Morador não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletarMorador(@PathVariable Long id) {
        log.info("Iniciando a exclusão do morador com ID: {}.", id);
        moradorService.deletarMorador(id);
        log.info("Morador com ID: {} excluída com sucesso.", id);
        return ResponseEntity.noContent().build();
    }
}
