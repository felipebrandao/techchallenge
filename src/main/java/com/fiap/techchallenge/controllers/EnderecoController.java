package com.fiap.techchallenge.controllers;

import com.fiap.techchallenge.dtos.EnderecoDTO;
import com.fiap.techchallenge.services.EnderecoService;
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
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> obterEnderecos(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.getEnderecoById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<EnderecoDTO>> buscarEnderecoPorFiltro(
            @RequestParam(required = false) String rua,
            @RequestParam(required = false) String bairro,
            @RequestParam(required = false) String cidade) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.buscarEnderecoPorFiltro(rua, bairro, cidade));
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> cadastro(@Valid @RequestBody EnderecoDTO enderecoDTO, HttpServletRequest request, HttpServletResponse response) {
        log.info("Inicio da requisição - EnderecoController - cadastro");
        EnderecoDTO enderecoGravado = enderecoService.cadastrarEndereco(enderecoDTO);
        log.info("Fim da requisição - EnderecoController - cadastro");
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoGravado);
    }

    @PutMapping("{id}")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@PathVariable Long id, @RequestBody EnderecoDTO enderecoDTO,
                                                         HttpServletRequest request, HttpServletResponse response) {
        log.info("Iniciando a atualização da pessoa com ID: {}.", id);
        EnderecoDTO enderecoAtualizado = enderecoService.atualizarEndereco(id, enderecoDTO);
        log.info("Pessoa com ID: {} atualizada com sucesso.", id);
        return ResponseEntity.status(HttpStatus.OK).body(enderecoAtualizado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("Iniciando exclusão do endereço com ID: " + id);
        enderecoService.deletarEndereco(id);
        log.info("Endereço com ID: {} excluída com sucesso.", id);
        return ResponseEntity.ok("Endereço deletado com sucesso");
    }
}
