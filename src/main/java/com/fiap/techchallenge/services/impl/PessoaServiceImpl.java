package com.fiap.techchallenge.services.impl;

import com.fiap.techchallenge.dtos.PessoaDTO;
import com.fiap.techchallenge.entities.Pessoa;
import com.fiap.techchallenge.exceptions.PessoaCamposNaoPreenchidosException;
import com.fiap.techchallenge.exceptions.PessoaExisteException;
import com.fiap.techchallenge.exceptions.PessoaNaoEncontradaException;
import com.fiap.techchallenge.mappers.PessoaMapper;
import com.fiap.techchallenge.repositories.PessoaRepository;
import com.fiap.techchallenge.services.PessoaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper;

    @Autowired
    public PessoaServiceImpl(PessoaRepository pessoaRepository, PessoaMapper pessoaMapper) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaMapper = pessoaMapper;
    }

    @Override
    public PessoaDTO cadastrarPessoa(PessoaDTO pessoaDTO) {
        log.info("Inicio do metódo - PessoaServiceImpl - cadastrarPessoa");
        isExistePessoa(pessoaDTO.getCpf());
        Pessoa pessoa = pessoaRepository.save(pessoaMapper.toEntity(pessoaDTO));
        log.info("Fim do metódo - PessoaServiceImpl - cadastrarPessoa");
        return pessoaMapper.toDTO(pessoa);
    }

    @Override
    public PessoaDTO atualizarPessoa(Long id, PessoaDTO pessoaDTO) {
        log.info("Inicio do metódo - PessoaServiceImpl - atualizarPessoa");

        if (!pessoaDTO.isPeloMenosUmCampoPreenchido()) throw new PessoaCamposNaoPreenchidosException("Pelo menos um campo deve estar preenchido.");

        Pessoa pessoaEncontrada = pessoaRepository.findByIdAndCPF(id, pessoaDTO.getCpf());

        if (pessoaEncontrada == null)
            throw new PessoaNaoEncontradaException("Pessoa com este id: " + id + " e CPF: " + pessoaDTO.getCpf() + " não encontrada.");

        Pessoa pessoaUpdate = toEntityUpdate(pessoaDTO, pessoaEncontrada);
        pessoaUpdate = pessoaRepository.save(pessoaUpdate);

        log.info("Fim do metódo - PessoaServiceImpl - atualizarPessoa");
        return pessoaMapper.toDTO(pessoaUpdate);
    }

    public void deletarPessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa com ID " + id + " não encontrada."));
        pessoaRepository.delete(pessoa);
    }

    @Override
    public PessoaDTO getPessoaById(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa com ID " + id + " não encontrada."));
        return pessoaMapper.toDTO(pessoa);
    }

    @Override
    public List<PessoaDTO> buscarPessoasPorNomeECPF(String nome, String cpf) {
        List<Pessoa> pessoas = pessoaRepository.findByNomeAndCpf(nome, cpf);
        return pessoas.stream()
                .map(pessoaMapper::toDTO)
                .collect(Collectors.toList());
    }

    private Pessoa toEntityUpdate(PessoaDTO pessoaDTO, Pessoa pessoa) {
        if (pessoaDTO.getNome() != null) pessoa.setNome(pessoaDTO.getNome());
        if (pessoaDTO.getSexo() != null) pessoa.setSexo(pessoaDTO.getSexo());
        if (pessoaDTO.getDataDeNascimento() != null) pessoa.setDataDeNascimento(pessoaDTO.getDataDeNascimento());
        return pessoa;
    }

    private void isExistePessoa(String cpf) {
        log.info("Inicio do metódo - PessoaServiceImpl - isExistePessoa");
        boolean existePessoa = pessoaRepository.existsByCpf(cpf);
        if (existePessoa) {
            throw new PessoaExisteException("Pessoa já cadastrada com este CPF.");
        }
        log.info("Fim do metódo - PessoaServiceImpl - isExistePessoa");
    }
}
