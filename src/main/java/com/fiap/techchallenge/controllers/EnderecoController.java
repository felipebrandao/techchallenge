package com.fiap.techchallenge.controllers;

import com.fiap.techchallenge.dtos.EnderecoDTO;
import com.fiap.techchallenge.exceptions.EnderecoNaoEncontradoException;
import com.fiap.techchallenge.services.EnderecoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @Autowired
    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> obterEndereco(@PathVariable Long id) {
        log.info("Obtendo endereço com ID: {}", id);
        EnderecoDTO endereco = enderecoService.getEnderecoById(id);
        return ResponseEntity.ok(endereco);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<EnderecoDTO>> buscarEnderecoPorFiltro(
            @RequestParam(required = false) String rua,
            @RequestParam(required = false) String bairro,
            @RequestParam(required = false) String cidade) {
        log.info("Buscando endereços por filtro: rua={}, bairro={}, cidade={}", rua, bairro, cidade);
        List<EnderecoDTO> enderecos = enderecoService.buscarEnderecoPorFiltro(rua, bairro, cidade);
        return ResponseEntity.ok(enderecos);
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> cadastrarEndereco(@Valid @RequestBody EnderecoDTO enderecoDTO) {
        log.info("Cadastrando novo endereço: {}", enderecoDTO);
        EnderecoDTO enderecoCadastrado = enderecoService.cadastrarEndereco(enderecoDTO);
        return ResponseEntity.status(CREATED).body(enderecoCadastrado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@PathVariable Long id, @Valid @RequestBody EnderecoDTO enderecoDTO) {
        log.info("Atualizando endereço com ID: {}", id);
        EnderecoDTO enderecoAtualizado = enderecoService.atualizarEndereco(id, enderecoDTO);
        return ResponseEntity.ok(enderecoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {
        log.info("Excluindo endereço com ID: {}", id);
        try {
            enderecoService.deletarEndereco(id);
            return ResponseEntity.noContent().build();
        } catch (EnderecoNaoEncontradoException e) {
            log.error("Endereço com ID {} não encontrado.", id);
            return ResponseEntity.notFound().build();
        }
    }
}
