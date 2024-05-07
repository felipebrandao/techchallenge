package com.fiap.techchallenge.pessoa.controllers;

import com.fiap.techchallenge.pessoa.dtos.PessoaDTO;
import com.fiap.techchallenge.pessoa.services.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/pessoa")
@Tag(name = "Pessoa", description = "Endpoints para gerenciamento de pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    @Operation(summary = "Cadastrar pessoa", description = "Cadastra uma nova pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pessoa cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<PessoaDTO> cadastrar(@Valid @RequestBody PessoaDTO pessoaDTO,
                                               HttpServletRequest request, HttpServletResponse response){
        log.info("Iniciando o cadastro de pessoa.");
        PessoaDTO pessoaGravada =pessoaService.cadastrarPessoa(pessoaDTO);
        log.info("Pessoa cadastrada com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaGravada);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar pessoa", description = "Atualiza uma pessoa a partir do seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<PessoaDTO> atualizar(@PathVariable Long id, @RequestBody PessoaDTO pessoaDTO,
                                            HttpServletRequest request, HttpServletResponse response){
        log.info("Iniciando a atualização da pessoa com ID: {}.", id);
        PessoaDTO pessoaAtualizada = pessoaService.atualizarPessoa(id, pessoaDTO);
        log.info("Pessoa com ID: {} atualizada com sucesso.", id);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaAtualizada);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar pessoa", description = "Deleta uma pessoa a partir do seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pessoa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("Iniciando a exclusão da pessoa com ID: {}.", id);
        pessoaService.deletarPessoa(id);
        log.info("Pessoa com ID: {} excluída com sucesso.", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter pessoa por ID", description = "Obtém uma pessoa a partir do seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<PessoaDTO> getPessoaById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.getPessoaById(id));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar pessoas por filtro", description = "Busca pessoas a partir de filtros opcionais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoas encontradas"),
            @ApiResponse(responseCode = "404", description = "Pessoas não encontradas"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<PessoaDTO>> buscarPessoasPorNomeECPF(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.buscarPessoasPorNomeECPF(nome, cpf));
    }
}
