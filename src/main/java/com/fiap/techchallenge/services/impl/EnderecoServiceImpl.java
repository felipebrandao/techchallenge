package com.fiap.techchallenge.services.impl;

import com.fiap.techchallenge.dtos.EnderecoDTO;
import com.fiap.techchallenge.entities.Endereco;
import com.fiap.techchallenge.enums.EstadoEnum;
import com.fiap.techchallenge.exceptions.EnderecoCamposNaoPreenchidosException;
import com.fiap.techchallenge.exceptions.EnderecoExistenteException;
import com.fiap.techchallenge.exceptions.EnderecoNaoEncontradoException;
import com.fiap.techchallenge.mappers.EnderecoMapper;
import com.fiap.techchallenge.repositories.EnderecoRepository;
import com.fiap.techchallenge.services.EnderecoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EnderecoServiceImpl implements EnderecoService {

    private EnderecoRepository enderecoRepository;

    private EnderecoMapper enderecoMapper;

    @Autowired
    public EnderecoServiceImpl(EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }


    public EnderecoDTO getEnderecoById(Long id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new EnderecoNaoEncontradoException("Endereço com ID " + id + " não encontrado."));

        return enderecoMapper.toDTO(endereco);
    }

    public List<EnderecoDTO> buscarEnderecoPorFiltro(String rua, String bairro, String cidade) {
        List<Endereco> enderecos = enderecoRepository.buscarEnderecosPorFiltros(rua, bairro, cidade);

        if (enderecos.isEmpty()) {
            throw new EnderecoNaoEncontradoException("Nenhum endereço encontrado com os filtros fornecidos.");
        }

        return enderecos.stream()
                .map(enderecoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EnderecoDTO cadastrarEndereco(EnderecoDTO enderecoDTO) {
        log.info("Inicio do método - EnderecoServiceImpl - cadastrarEndereco");
        verificarEnderecoExistente(enderecoDTO);
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        endereco = enderecoRepository.save(endereco);
        log.info("Fim do método - EnderecoServiceImpl - cadastrarEndereco");
        return enderecoMapper.toDTO(endereco);
    }

    public EnderecoDTO atualizarEndereco(Long id, EnderecoDTO enderecoDTO) {
        log.info("Início do método - EnderecoServiceImpl - atualizarEndereco");

        if (!enderecoDTO.isPeloMenosUmCampoPreenchido()) {
            throw new EnderecoCamposNaoPreenchidosException("Pelo menos um campo deve estar preenchido.");
        }

        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new EnderecoNaoEncontradoException("Endereço com este id: " + id + " não encontrado."));

        endereco = toEntityUpdate(enderecoDTO, endereco);
        endereco = enderecoRepository.save(endereco);
        log.info("Fim do método - EnderecoServiceImpl - atualizarEndereco");

        return enderecoMapper.toDTO(endereco);
    }

    public void deletarEndereco(Long id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new EnderecoNaoEncontradoException("Pessoa com ID " + id + " não encontrada."));

        enderecoRepository.delete(endereco);
    }

    private void verificarEnderecoExistente(EnderecoDTO enderecoDTO) {
        String rua = enderecoDTO.getRua();
        Integer numero = enderecoDTO.getNumero();
        String bairro = enderecoDTO.getBairro();
        String cidade = enderecoDTO.getCidade();
        EstadoEnum estado = enderecoDTO.getEstado();

        boolean existeEndereco = enderecoRepository.isExisteEnderecoCadastrado(rua, numero, bairro, cidade, estado);

        if (existeEndereco) {
            throw new EnderecoExistenteException("Endereço já cadastrado com esses campos.");
        }
    }

    private Endereco toEntityUpdate(EnderecoDTO enderecoDTO, Endereco endereco) {
        if (enderecoDTO.getRua() != null) endereco.setRua(enderecoDTO.getRua());
        if (enderecoDTO.getNumero() != null) endereco.setNumero(enderecoDTO.getNumero());
        if (enderecoDTO.getBairro() != null) endereco.setBairro(enderecoDTO.getBairro());
        if (enderecoDTO.getCidade() != null) endereco.setCidade(enderecoDTO.getCidade());
        if (enderecoDTO.getEstado() != null) endereco.setEstado(enderecoDTO.getEstado());
        return endereco;
    }
}
