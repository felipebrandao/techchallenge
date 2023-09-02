package com.fiap.techchallenge.services.impl;

import com.fiap.techchallenge.dtos.EnderecoDTO;
import com.fiap.techchallenge.entities.Endereco;
import com.fiap.techchallenge.exceptions.EnderecoNaoEncontradoException;
import com.fiap.techchallenge.mappers.EnderecoMapper;
import com.fiap.techchallenge.repositories.EnderecoRepository;
import com.fiap.techchallenge.services.EnderecoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Override
    public List<EnderecoDTO> obterEnderecos() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return enderecos.stream()
                .map(endereco -> enderecoMapper.toDTO(endereco))
                .collect(Collectors.toList());
    }

    @Override
    public List<EnderecoDTO> buscarEnderecosPorFiltros(String rua, String bairro, String cidade) {
        List<Endereco> enderecos = enderecoRepository.buscarEnderecosPorFiltros(rua, bairro, cidade);
        return enderecoMapper.toDTOs(enderecos);
    }

    @Override
    public EnderecoDTO cadastrarEndereco(EnderecoDTO enderecoDTO) {
        log.info("Inicio do metódo - EnderecoServiceImpl - cadastrarEndereco");
        Endereco endereco = enderecoRepository.save(enderecoMapper.toEntity(enderecoDTO));
        log.info("Fim do metódo - EnderecoServiceImpl - cadastrarEndereco");
        return enderecoMapper.toDTO(endereco);
    }

    @Override
    public void deletarEndereco(Long id) {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);
        if (enderecoOptional.isPresent()) {
            Endereco enderecoExcluido = enderecoOptional.get();
            enderecoRepository.deleteById(id);
            enderecoMapper.toDTO(enderecoExcluido);
        } else {
            throw new EnderecoNaoEncontradoException("Endereço com ID " + id + " não encontrado.");
        }
    }
}
