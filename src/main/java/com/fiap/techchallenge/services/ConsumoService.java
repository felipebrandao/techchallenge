package com.fiap.techchallenge.services;

import com.fiap.techchallenge.dtos.ConsumoDTO;

import java.time.LocalDate;
import java.util.List;

public interface ConsumoService {
    ConsumoDTO registrarConsumo(ConsumoDTO consumoDTO);

    ConsumoDTO encontrarConsumoPorId(Long id);

    void deletarConsumo(Long id);

    List<ConsumoDTO> pesquisarConsumos(LocalDate dataInicio,
                                       LocalDate dataFim,
                                       Long idPessoa,
                                       Long idEndereco,
                                       Long idEletrodomestico);
}
