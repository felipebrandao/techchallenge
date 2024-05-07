package com.fiap.techchallenge.eletrodomestico.controllers;

import com.fiap.techchallenge.eletrodomestico.dtos.EletrodomesticoDTO;
import com.fiap.techchallenge.eletrodomestico.services.EletrodomesticoService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/eletrodomestico")
@Tag(name = "Eletrodomestico", description = "Endpoints para gerenciamento de eletrodomésticos")
public class EletrodomesticoController {

    @Autowired
    private EletrodomesticoService eletrodomesticoService;

    @PostMapping
    @Operation(summary = "Cadastrar eletrodoméstico", description = "Cadastra um novo eletrodoméstico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Eletrodoméstico cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<EletrodomesticoDTO> cadastrar(@Valid @RequestBody EletrodomesticoDTO eletrodomesticoDTO, HttpServletRequest request, HttpServletResponse response){
        log.info("Inicio da requisição - EletrodomesticoController - cadastro");
        EletrodomesticoDTO enderecoGravado = eletrodomesticoService.cadastrarEletrodomestico(eletrodomesticoDTO);
        log.info("Fim da requisição - EletrodomesticoController - cadastro");
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoGravado);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar eletrodoméstico", description = "Atualiza um eletrodoméstico a partir do seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eletrodoméstico atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Eletrodoméstico não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<EletrodomesticoDTO> atualizar(@PathVariable Long id, @RequestBody EletrodomesticoDTO eletrodomesticoDTO,
                                                        HttpServletRequest request, HttpServletResponse response) {
        log.info("Iniciando a atualização do eletrodoméstico com ID: {}.", id);
        EletrodomesticoDTO eletrodomesticoAtualizado = eletrodomesticoService.atualizarEletrodomestico(id, eletrodomesticoDTO);
        log.info("Eletrodoméstico com ID {} atualizado com sucesso.", id);
        return ResponseEntity.status(HttpStatus.OK).body(eletrodomesticoAtualizado);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar eletrodoméstico", description = "Deleta um eletrodoméstico a partir do seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Eletrodoméstico deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Eletrodoméstico não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("Iniciando a exclusão do eletrodoméstico com ID {}.", id);
        eletrodomesticoService.deletarEletrodomestico(id);
        log.info("Eletrodoméstico dom ID {} excluído com sucesso.", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter eletrodoméstico por ID", description = "Obtém um eletrodoméstico a partir do seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eletrodoméstico encontrado"),
            @ApiResponse(responseCode = "404", description = "Eletrodoméstico não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<EletrodomesticoDTO> getEletrodomesticoById(@PathVariable Long id) {
        log.info("Buiscanoo o eletrodoméstico com ID: {}.", id);
        EletrodomesticoDTO eletrodomestico = eletrodomesticoService.getEletrodomesticoById(id);
        log.info("Eletrodomestico com ID: {} retornado com sucesso.", id);
        return ResponseEntity.status(HttpStatus.OK).body(eletrodomestico);
    }

    @GetMapping("/pesquisar")
    @Operation(summary = "Pesquisar eletrodomésticos", description = "Pesquisa eletrodomésticos a partir de filtros opcionais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eletrodomésticos encontrados"),
            @ApiResponse(responseCode = "404", description = "Eletrodomésticos não encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<EletrodomesticoDTO>> pesquisarEletrodomesticos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) Double potencia) {
        log.info("Iniciando a pesquisa de eletrodomésticos.");

        List<EletrodomesticoDTO> eletrodomesticos = eletrodomesticoService.pesquisarEletrodomesticos(
                nome, modelo, potencia
        );

        log.info("Pesquisa de eletrodomésticos concluída.");
        return ResponseEntity.status(HttpStatus.OK).body(eletrodomesticos);
    }
}
