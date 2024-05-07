package com.fiap.techchallenge.endereco.controllers;

import com.fiap.techchallenge.endereco.dtos.EnderecoDTO;
import com.fiap.techchallenge.endereco.services.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/api/endereco")
@Tag(name = "Endereço", description = "Endpoints para gerenciamento de endereços")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @Autowired
    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter endereço por ID", description = "Obtém um endereço a partir do seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<EnderecoDTO> obterEndereco(@PathVariable Long id) {
        log.info("Obtendo endereço com ID: {}", id);
        EnderecoDTO endereco = enderecoService.getEnderecoById(id);
        return ResponseEntity.ok(endereco);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar endereços por filtro", description = "Busca endereços a partir de filtros opcionais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereços encontrados"),
            @ApiResponse(responseCode = "404", description = "Endereços não encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<EnderecoDTO>> buscarEnderecoPorFiltro(
            @RequestParam(required = false) String rua,
            @RequestParam(required = false) String bairro,
            @RequestParam(required = false) String cidade) {
        log.info("Buscando endereços por filtro: rua={}, bairro={}, cidade={}", rua, bairro, cidade);
        List<EnderecoDTO> enderecos = enderecoService.buscarEnderecoPorFiltro(rua, bairro, cidade);
        return ResponseEntity.ok(enderecos);
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo endereço", description = "Cadastra um novo endereço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<EnderecoDTO> cadastrarEndereco(@Valid @RequestBody EnderecoDTO enderecoDTO) {
        log.info("Cadastrando novo endereço: {}", enderecoDTO);
        EnderecoDTO enderecoCadastrado = enderecoService.cadastrarEndereco(enderecoDTO);
        return ResponseEntity.status(CREATED).body(enderecoCadastrado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar endereço", description = "Atualiza um endereço a partir do seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@PathVariable Long id, @Valid @RequestBody EnderecoDTO enderecoDTO) {
        log.info("Atualizando endereço com ID: {}", id);
        EnderecoDTO enderecoAtualizado = enderecoService.atualizarEndereco(id, enderecoDTO);
        return ResponseEntity.ok(enderecoAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar endereço", description = "Deleta um endereço a partir do seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {
        log.info("Excluindo endereço com ID: {}", id);
        enderecoService.deletarEndereco(id);
        return ResponseEntity.noContent().build();
    }
}
