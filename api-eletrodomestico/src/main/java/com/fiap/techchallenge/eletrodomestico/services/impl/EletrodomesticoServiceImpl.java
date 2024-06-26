package com.fiap.techchallenge.eletrodomestico.services.impl;

import com.fiap.techchallenge.eletrodomestico.dtos.EletrodomesticoDTO;
import com.fiap.techchallenge.eletrodomestico.entities.Eletrodomestico;
import com.fiap.techchallenge.eletrodomestico.exceptions.EletrodomesticoNaoEncontradoException;
import com.fiap.techchallenge.eletrodomestico.mappers.EletrodomesticoMapper;
import com.fiap.techchallenge.eletrodomestico.repositories.EletrodomesticoRepository;
import com.fiap.techchallenge.eletrodomestico.services.EletrodomesticoService;
import com.fiap.techchallenge.eletrodomestico.exceptions.EletrodomesticoCamposNaoPreenchidosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EletrodomesticoServiceImpl implements EletrodomesticoService {

    private final EletrodomesticoMapper eletrodomesticoMapper;

    private final EletrodomesticoRepository eletrodomesticoRepository;

    @Autowired
    public EletrodomesticoServiceImpl(EletrodomesticoRepository eletrodomesticoRepository,
                                      EletrodomesticoMapper eletrodomesticoMapper) {
        this.eletrodomesticoRepository = eletrodomesticoRepository;
        this.eletrodomesticoMapper = eletrodomesticoMapper;
    }

    @Override
    public EletrodomesticoDTO cadastrarEletrodomestico(EletrodomesticoDTO eletrodomesticoDTO) {
        log.info("Inicio do metódo - EletrodomesticoServiceImpl - cadastrarEndereco");
        Eletrodomestico eletrodomestico = eletrodomesticoRepository.save(eletrodomesticoMapper.toEntity(eletrodomesticoDTO));
        log.info("Fim da requisição - EletrodomesticoServiceImpl - cadastrarEndereco");
        return eletrodomesticoMapper.toDTO(eletrodomestico);
    }

    @Override
    public EletrodomesticoDTO atualizarEletrodomestico(Long id, EletrodomesticoDTO eletrodomesticoDTO) {
        log.info("Início do método - EletrodomesticoServiceImpl - atualizarEletrodomestico");

        if (!eletrodomesticoDTO.isPeloMenosUmCampoPreenchido()) throw new EletrodomesticoCamposNaoPreenchidosException("Pelo menos um campo deve estar preenchido.");

        Eletrodomestico eletrodomesticoEncontrado = eletrodomesticoRepository.findById(id)
                .orElseThrow(() -> new EletrodomesticoNaoEncontradoException("Eletrodoméstico com ID " + id + " não encontrado"));

        Eletrodomestico eletrodomesticoUpdate = toEntityUpdate(eletrodomesticoDTO, eletrodomesticoEncontrado);
        eletrodomesticoUpdate = eletrodomesticoRepository.save(eletrodomesticoUpdate);

        log.info("Fim do método - EletrodomesticoServiceImpl - atualizarEletrodomestico");
        return eletrodomesticoMapper.toDTO(eletrodomesticoUpdate);
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

    private Eletrodomestico toEntityUpdate(EletrodomesticoDTO eletrodomesticoDTO, Eletrodomestico eletrodomestico) {
        if (eletrodomesticoDTO.getNome() != null) eletrodomestico.setNome(eletrodomesticoDTO.getNome());
        if (eletrodomesticoDTO.getModelo() != null) eletrodomestico.setModelo(eletrodomesticoDTO.getModelo());
        if (eletrodomesticoDTO.getPotencia() != null) eletrodomestico.setPotencia(eletrodomesticoDTO.getPotencia());
        return eletrodomestico;
    }
}
