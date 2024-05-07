package com.fiap.techchallenge.morador.services.impl;

import com.fiap.techchallenge.morador.dtos.MoradorDTO;
import com.fiap.techchallenge.morador.entities.Morador;
import com.fiap.techchallenge.morador.exceptions.MoradorCadastroNoEnderecoException;
import com.fiap.techchallenge.morador.exceptions.MoradorNaoEncontradaException;
import com.fiap.techchallenge.morador.feign.EnderecoFeignClient;
import com.fiap.techchallenge.morador.feign.PessoaFeignClient;
import com.fiap.techchallenge.morador.feign.dtos.PessoaDTO;
import com.fiap.techchallenge.morador.mappers.MoradorMapper;
import com.fiap.techchallenge.morador.repositories.MoradorRepository;
import com.fiap.techchallenge.morador.services.MoradorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MoradorServiceImpl implements MoradorService {

    private final MoradorRepository moradorRepository;
    private final MoradorMapper moradorMapper;
    private final PessoaFeignClient pessoaFeignClient;
    private final EnderecoFeignClient enderecoFeignClient;

    @Autowired
    public MoradorServiceImpl(MoradorRepository moradorRepository, MoradorMapper moradorMapper, PessoaFeignClient pessoaFeignClient, EnderecoFeignClient enderecoFeignClient) {
        this.moradorRepository = moradorRepository;
        this.moradorMapper = moradorMapper;
        this.pessoaFeignClient = pessoaFeignClient;
        this.enderecoFeignClient = enderecoFeignClient;
    }

    @Override
    public MoradorDTO criarMorador(MoradorDTO moradorDTO) {
        log.info("Inicio do metódo - MoradorServiceImpl - criarMorador");

        try {
            PessoaDTO pessoaDTO = pessoaFeignClient.getPessoaById(moradorDTO.getIdPessoa());
        } catch (Exception e) {
            throw new MoradorNaoEncontradaException("Pessoa com ID " + moradorDTO.getIdPessoa() + " não encontrada.");
        }

        try {
            enderecoFeignClient.getEnderecoById(moradorDTO.getIdEndereco());
        } catch (Exception e) {
            throw new MoradorNaoEncontradaException("Endereço com ID " + moradorDTO.getIdEndereco() + " não encontrado.");
        }

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
