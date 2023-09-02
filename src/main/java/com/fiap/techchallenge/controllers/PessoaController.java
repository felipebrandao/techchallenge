package com.fiap.techchallenge.controllers;

import com.fiap.techchallenge.dtos.PessoaDTO;
import com.fiap.techchallenge.services.PessoaService;
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
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<PessoaDTO> cadastrar(@Valid @RequestBody PessoaDTO pessoaDTO,
                                   HttpServletRequest request, HttpServletResponse response){
        log.info("Iniciando o cadastro de pessoa.");
        PessoaDTO pessoaGravada =pessoaService.cadastrarPessoa(pessoaDTO);
        log.info("Pessoa cadastrada com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaGravada);
    }

    @PutMapping("{id}")
    public ResponseEntity<PessoaDTO> atualizar(@PathVariable Long id, @RequestBody PessoaDTO pessoaDTO,
                                            HttpServletRequest request, HttpServletResponse response){
        log.info("Iniciando a atualização da pessoa com ID: {}.", id);
        PessoaDTO pessoaAtualizada = pessoaService.atualizarPessoa(id, pessoaDTO);
        log.info("Pessoa com ID: {} atualizada com sucesso.", id);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaAtualizada);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("Iniciando a exclusão da pessoa com ID: {}.", id);
        pessoaService.deletarPessoa(id);
        log.info("Pessoa com ID: {} excluída com sucesso.", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> getPessoaById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.getPessoaById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<PessoaDTO>> buscarPessoasPorNomeECPF(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.buscarPessoasPorNomeECPF(nome, cpf));
    }
}
