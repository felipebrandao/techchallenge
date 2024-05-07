package com.fiap.techchallenge.consumo.services.impl;

import com.fiap.techchallenge.consumo.dtos.ConsumoDTO;
import com.fiap.techchallenge.consumo.entities.Consumo;
import com.fiap.techchallenge.consumo.exceptions.ConsumoNaoEncontradaException;
import com.fiap.techchallenge.consumo.feign.MoradorFeignClient;
import com.fiap.techchallenge.consumo.feign.dtos.MoradorDTO;
import com.fiap.techchallenge.consumo.mappers.ConsumoMapper;
import com.fiap.techchallenge.consumo.repositories.ConsumoRepository;
import com.fiap.techchallenge.consumo.services.ConsumoService;
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
    private final MoradorFeignClient moradorFeignClient;

    @Autowired
    public ConsumoServiceImpl(ConsumoRepository consumoRepository, ConsumoMapper consumoMapper, MoradorFeignClient moradorFeignClient) {
        this.consumoRepository = consumoRepository;
        this.consumoMapper = consumoMapper;
        this.moradorFeignClient = moradorFeignClient;
    }

    @Override
    public ConsumoDTO registrarConsumo(ConsumoDTO consumoDTO) {
        log.info("Inicio do metódo - ConsumoServiceImpl - registrarConsumo");

        try {
            moradorFeignClient.getMoradorById(consumoDTO.getIdMorador());
        } catch (Exception e) {
            throw new ConsumoNaoEncontradaException("Morador com ID " + consumoDTO.getIdMorador() + " não encontrado.");
        }

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
            Long idEletrodomestico) {
        log.info("Iniciando a pesquisa de consumos.");

        List<Consumo> consumos = consumoRepository.pesquisarConsumos(dataInicio, dataFim, idPessoa, idEletrodomestico);

        log.info("Pesquisa de consumos concluída.");
        return consumoMapper.toDTOList(consumos);
    }
}
