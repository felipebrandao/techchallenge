package com.fiap.techchallenge.controllers;

import com.fiap.techchallenge.dtos.EnderecoDTO;
import com.fiap.techchallenge.exceptions.EnderecoNaoEncontradoException;
import com.fiap.techchallenge.services.EnderecoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> obterEnderecos() {
        List<EnderecoDTO> enderecos = enderecoService.obterEnderecos();
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<EnderecoDTO>> buscarEnderecosPorFiltros(
            @RequestParam(required = false) String rua,
            @RequestParam(required = false) String bairro,
            @RequestParam(required = false) String cidade) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.buscarEnderecosPorFiltros(rua, bairro, cidade));
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> cadastro(@Valid @RequestBody EnderecoDTO enderecoDTO, HttpServletRequest request, HttpServletResponse response) {
        log.info("Inicio da requisição - EnderecoController - cadastro");
        EnderecoDTO enderecoGravado = enderecoService.cadastrarEndereco(enderecoDTO);
        log.info("Fim da requisição - EnderecoController - cadastro");
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoGravado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("Iniciando exclusão do endereço com ID: " + id);
        try {
            enderecoService.deletarEndereco(id);
            return ResponseEntity.ok("Endereço deletado com sucesso");
        } catch (EnderecoNaoEncontradoException e) {
            log.error("Endereço com ID " + id + " não encontrado.");
            return ResponseEntity.status(NOT_FOUND).body("Endereço não encontrado");
        }
    }
}
