package com.fiap.techchallenge.services.impl;

import com.fiap.techchallenge.dtos.MoradorDTO;
import com.fiap.techchallenge.entities.Morador;
import com.fiap.techchallenge.exceptions.MoradorCadastroNoEnderecoException;
import com.fiap.techchallenge.exceptions.MoradorNaoEncontradaException;
import com.fiap.techchallenge.mappers.MoradorMapper;
import com.fiap.techchallenge.repositories.MoradorRepository;
import com.fiap.techchallenge.services.EnderecoService;
import com.fiap.techchallenge.services.MoradorService;
import com.fiap.techchallenge.services.PessoaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MoradorServiceImpl implements MoradorService {

    private final MoradorRepository moradorRepository;
    private final MoradorMapper moradorMapper;
    private final PessoaService pessoaService;
    private final EnderecoService enderecoService;

    @Autowired
    public MoradorServiceImpl(MoradorRepository moradorRepository, MoradorMapper moradorMapper,
                              PessoaService pessoaService, EnderecoService enderecoService) {
        this.moradorRepository = moradorRepository;
        this.moradorMapper = moradorMapper;
        this.pessoaService = pessoaService;
        this.enderecoService = enderecoService;
    }

    @Override
    public MoradorDTO criarMorador(MoradorDTO moradorDTO) {
        log.info("Inicio do metódo - MoradorServiceImpl - criarMorador");
        pessoaService.getPessoaById(moradorDTO.getIdPessoa());
        //enderecoService.getEnderecoById(moradorDTO.getIdEndereco());
        boolean existePessoaCadastradaNoEndereco = moradorRepository.isExistePessoaCadastradaNoEndereco(moradorDTO.getIdEndereco(), moradorDTO.getIdPessoa());
        if(existePessoaCadastradaNoEndereco) {
            throw new MoradorCadastroNoEnderecoException("A pessoa já está cadastrada neste endereço.");
        }
        Morador morador = moradorRepository.save(moradorMapper.toEntity(moradorDTO));
        log.info("Fim do metódo - MoradorServiceImpl - criarMorador");
        return moradorMapper.toDTO(morador);
    }

    @Override
    public MoradorDTO encontrarMoradorPorId(Long id) {
        Morador morador = moradorRepository.findById(id)
                .orElseThrow(() -> new MoradorNaoEncontradaException("Morador com ID " + id + " não encontrado."));

        return moradorMapper.toDTO(morador);
    }

    @Override
    public void deletarMorador(Long id) {
        Morador morador = moradorRepository.findById(id)
                .orElseThrow(() -> new MoradorNaoEncontradaException("Morador com ID " + id + " não encontrado."));

        moradorRepository.delete(morador);
    }

    @Override
    public List<MoradorDTO> pesquisarMoradores(Long idPessoa, Long idEndereco) {
        log.info("Iniciando a pesquisa de moradores.");

        List<Morador> moradores = moradorRepository.pesquisarMoradores(idPessoa, idEndereco);

        log.info("Pesquisa de moradores concluída.");
        return moradorMapper.toDTOList(moradores);
    }
}
