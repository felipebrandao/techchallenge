package com.fiap.techchallenge.services.impl;

import com.fiap.techchallenge.dtos.ConsumoDTO;
import com.fiap.techchallenge.entities.Consumo;
import com.fiap.techchallenge.exceptions.ConsumoNaoEncontradaException;
import com.fiap.techchallenge.mappers.ConsumoMapper;
import com.fiap.techchallenge.repositories.ConsumoRepository;
import com.fiap.techchallenge.services.ConsumoService;
import com.fiap.techchallenge.services.MoradorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ConsumoServiceImpl implements ConsumoService {

    private final ConsumoRepository consumoRepository;
    private final ConsumoMapper consumoMapper;
    private final MoradorService moradorService;

    @Autowired
    public ConsumoServiceImpl(ConsumoRepository consumoRepository, ConsumoMapper consumoMapper, MoradorService moradorService) {
        this.consumoRepository = consumoRepository;
        this.consumoMapper = consumoMapper;
        this.moradorService = moradorService;
    }

    @Override
    public ConsumoDTO registrarConsumo(ConsumoDTO consumoDTO) {
        log.info("Inicio do metódo - ConsumoServiceImpl - registrarConsumo");

        moradorService.encontrarMoradorPorId(consumoDTO.getIdMorador());

        Consumo consumo = consumoRepository.save(consumoMapper.toEntity(consumoDTO));

        log.info("Fim do metódo - ConsumoServiceImpl - registrarConsumo");
        return consumoMapper.toDTO(consumo);
    }

    @Override
    public ConsumoDTO encontrarConsumoPorId(Long id) {
        Consumo consumo = consumoRepository.findById(id)
                .orElseThrow(() -> new ConsumoNaoEncontradaException("Consumo com ID " + id + " não encontrado."));

        return consumoMapper.toDTO(consumo);
    }

    @Override
    public void deletarConsumo(Long id) {
        Consumo consumo = consumoRepository.findById(id)
                .orElseThrow(() -> new ConsumoNaoEncontradaException("Consumo com ID " + id + " não encontrado."));

        consumoRepository.delete(consumo);
    }

    @Override
    public List<ConsumoDTO> pesquisarConsumos(
            LocalDate dataInicio,
            LocalDate dataFim,
            Long idPessoa,
            Long idEndereco,
            Long idEletrodomestico) {
        log.info("Iniciando a pesquisa de consumos.");

        List<Consumo> consumos = consumoRepository.pesquisarConsumos(dataInicio, dataFim, idPessoa, idEndereco, idEletrodomestico);

        log.info("Pesquisa de consumos concluída.");
        return consumoMapper.toDTOList(consumos);
    }
}
