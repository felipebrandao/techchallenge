package com.fiap.techchallenge.services.impl;

import com.fiap.techchallenge.dtos.EletrodomesticoDTO;
import com.fiap.techchallenge.entities.Eletrodomestico;
import com.fiap.techchallenge.exceptions.EletrodomesticoCamposNaoPreenchidosException;
import com.fiap.techchallenge.exceptions.EletrodomesticoNaoEncontradoException;
import com.fiap.techchallenge.mappers.EletrodomesticoMapper;
import com.fiap.techchallenge.repositories.EletrodomesticoRepository;
import com.fiap.techchallenge.services.EletrodomesticoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EletrodomesticoServiceImpl implements EletrodomesticoService {

    private EletrodomesticoMapper eletrodomesticoMapper;

    private EletrodomesticoRepository eletrodomesticoRepository;

    @Autowired
    public EletrodomesticoServiceImpl(EletrodomesticoRepository eletrodomesticoRepository,
                                      EletrodomesticoMapper eletrodomesticoMapper) {
        this.eletrodomesticoRepository = eletrodomesticoRepository;
        this.eletrodomesticoMapper = eletrodomesticoMapper;
    }

    @Override
    public EletrodomesticoDTO cadastrarEndereco(EletrodomesticoDTO eletrodomesticoDTO) {
        log.info("Inicio do metódo - EletrodomesticoServiceImpl - cadastrarEndereco");
        Eletrodomestico eletrodomestico = eletrodomesticoRepository.save(eletrodomesticoMapper.toEntity(eletrodomesticoDTO));
        log.info("Fim da requisição - EletrodomesticoServiceImpl - cadastrarEndereco");
        return eletrodomesticoMapper.toDTO(eletrodomestico);
    }

    @Override
    public EletrodomesticoDTO atualizarEletrodomestico(Long id, EletrodomesticoDTO eletrodomesticoDTO) {
        log.info("Início do método - EletrodomesticoServiceImpl - atualizarEletrodomestico");

        if (!isPeloMenosUmCampoPreenchido(eletrodomesticoDTO)) throw new EletrodomesticoCamposNaoPreenchidosException("Pelo menos um campo deve estar preenchido.");

        Eletrodomestico eletrodomesticoEncontrado = eletrodomesticoRepository.findById(id)
                .orElseThrow(() -> new EletrodomesticoNaoEncontradoException("Eletrodoméstico com ID " + id + " não encontrado"));

        eletrodomesticoEncontrado = toEntityUpdate(eletrodomesticoDTO, eletrodomesticoEncontrado);
        eletrodomesticoEncontrado = eletrodomesticoRepository.save(eletrodomesticoEncontrado);

        log.info("Fim do método - EletrodomesticoServiceImpl - atualizarEletrodomestico");
        return eletrodomesticoMapper.toDTO(eletrodomesticoEncontrado);
    }

    @Override
    public EletrodomesticoDTO getEletrodomesticoById(Long id) {
        Eletrodomestico eletrodomestico = eletrodomesticoRepository.findById(id)
                .orElseThrow(() -> new EletrodomesticoNaoEncontradoException("Eletrodoméstico com ID " + id + " não encontrado"));
        return eletrodomesticoMapper.toDTO(eletrodomestico);
    }

    @Override
    public void deletarEletrodomestico(Long id) {
        Eletrodomestico eletrodomestico = eletrodomesticoRepository.findById(id)
                .orElseThrow(() -> new EletrodomesticoNaoEncontradoException("Eletrodoméstico com ID " + id + " não encontrado"));

        eletrodomesticoRepository.delete(eletrodomestico);
    }

    @Override
    public List<EletrodomesticoDTO> pesquisarEletrodomesticos(
            String nome,
            String modelo,
            Double potencia) {
        log.info("Iniciando a pesquisa de eletrodomésticos.");

        List<Eletrodomestico> eletrodomesticos = eletrodomesticoRepository.pesquisarEletrodomesticos(
                nome, modelo, potencia);

        log.info("Pesquisa de eletrodomésticos concluída.");
        return eletrodomesticoMapper.toDTOList(eletrodomesticos);
    }

    private boolean isPeloMenosUmCampoPreenchido(EletrodomesticoDTO eletrodomesticoDTO) {
        return eletrodomesticoDTO.getNome() != null && eletrodomesticoDTO.getNome().isEmpty()
                || eletrodomesticoDTO.getModelo() != null && eletrodomesticoDTO.getModelo().isEmpty()
                || eletrodomesticoDTO.getPotencia() != null;
    }

    private Eletrodomestico toEntityUpdate(EletrodomesticoDTO eletrodomesticoDTO, Eletrodomestico eletrodomestico) {
        if (eletrodomesticoDTO.getNome() != null) eletrodomestico.setNome(eletrodomesticoDTO.getNome());
        if (eletrodomesticoDTO.getModelo() != null) eletrodomestico.setModelo(eletrodomesticoDTO.getModelo());
        if (eletrodomesticoDTO.getPotencia() != null) eletrodomestico.setPotencia(eletrodomesticoDTO.getPotencia());
        return eletrodomestico;
    }
}
