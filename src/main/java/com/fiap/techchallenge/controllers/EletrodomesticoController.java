package com.fiap.techchallenge.controllers;

import com.fiap.techchallenge.dtos.EletrodomesticoDTO;
import com.fiap.techchallenge.services.EletrodomesticoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/eletrodomestico")
public class EletrodomesticoController {

    @Autowired
    private EletrodomesticoService eletrodomesticoService;
    @PostMapping
    public ResponseEntity<EletrodomesticoDTO> cadastrar(@Valid @RequestBody EletrodomesticoDTO eletrodomesticoDTO, HttpServletRequest request, HttpServletResponse response){
        log.info("Inicio da requisição - EletrodomesticoController - cadastro");
        EletrodomesticoDTO enderecoGravado = eletrodomesticoService.cadastrarEndereco(eletrodomesticoDTO);
        log.info("Fim da requisição - EletrodomesticoController - cadastro");
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoGravado);
    }

    @PutMapping("{id}")
    public ResponseEntity<EletrodomesticoDTO> atualizar(@PathVariable Long id, @RequestBody EletrodomesticoDTO eletrodomesticoDTO,
                                                        HttpServletRequest request, HttpServletResponse response) {
        log.info("Iniciando a atualização do eletrodoméstico com ID: {}.", id);
        EletrodomesticoDTO eletrodomesticoAtualizado = eletrodomesticoService.atualizarEletrodomestico(id, eletrodomesticoDTO);
        log.info("Eletrodoméstico com ID {} atualizado com sucesso.", id);
        return ResponseEntity.status(HttpStatus.OK).body(eletrodomesticoAtualizado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("Iniciando a exclusão do eletrodoméstico com ID {}.", id);
        eletrodomesticoService.deletarEletrodomestico(id);
        log.info("Eletrodoméstico dom ID {} excluído com sucesso.", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EletrodomesticoDTO> getEletrodomesticoById(@PathVariable Long id) {
        log.info("Buiscanoo o eletrodoméstico com ID: {}.", id);
        EletrodomesticoDTO eletrodomestico = eletrodomesticoService.getEletrodomesticoById(id);
        log.info("Eletrodomestico com ID: {} retornado com sucesso.", id);
        return ResponseEntity.status(HttpStatus.OK).body(eletrodomestico);
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<EletrodomesticoDTO>> pesquisarEletrodomesticos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) Double potencia) {
        log.info("Iniciando a pesquisa de eletrodomésticos.");

        EletrodomesticoDTO dto = new EletrodomesticoDTO();
        dto.setNome(nome);
        dto.setModelo(modelo);
        dto.setPotencia(potencia);

        List<EletrodomesticoDTO> eletrodomesticos = eletrodomesticoService.pesquisarEletrodomesticos(dto);

        log.info("Pesquisa de eletrodomésticos concluída.");
        return ResponseEntity.status(HttpStatus.OK).body(eletrodomesticos);
    }
}
