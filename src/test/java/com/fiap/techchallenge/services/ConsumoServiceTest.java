package com.fiap.techchallenge.services;

import com.fiap.techchallenge.dtos.ConsumoDTO;
import com.fiap.techchallenge.dtos.MoradorDTO;
import com.fiap.techchallenge.entities.Consumo;
import com.fiap.techchallenge.mappers.ConsumoMapper;
import com.fiap.techchallenge.repositories.ConsumoRepository;
import com.fiap.techchallenge.services.impl.ConsumoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class ConsumoServiceTest {
    private ConsumoServiceImpl consumoService;

    @Mock
    private ConsumoRepository consumoRepository;

    @Spy
    private ConsumoMapper consumoMapper = Mappers.getMapper(ConsumoMapper.class);

    @Mock
    private MoradorService moradorService;

    @BeforeEach
    void setup() {
        consumoService = new ConsumoServiceImpl(consumoRepository, consumoMapper, moradorService);
    }

    @Test
    void testRegistrarConsumo() {
        ConsumoDTO consumoDTO = new ConsumoDTO();
        consumoDTO.setIdMorador(1L);

        Consumo consumoMock = new Consumo();
        Mockito.when(moradorService.encontrarMoradorPorId(1L)).thenReturn(new MoradorDTO());
        Mockito.when(consumoMapper.toEntity(consumoDTO)).thenReturn(consumoMock);
        Mockito.when(consumoRepository.save(any(Consumo.class))).thenReturn(consumoMock);

        ConsumoDTO result = consumoService.registrarConsumo(consumoDTO);

        assertNotNull(result);
    }

    @Test
    void testEncontrarConsumoPorId() {
        Long idConsumo = 1L;
        Consumo consumoMock = new Consumo();
        Mockito.when(consumoRepository.findById(idConsumo)).thenReturn(Optional.of(consumoMock));
        Mockito.when(consumoMapper.toDTO(consumoMock)).thenReturn(new ConsumoDTO());

        ConsumoDTO result = consumoService.encontrarConsumoPorId(idConsumo);

        assertNotNull(result);
    }

    @Test
    void testDeletarConsumo() {
        Long idConsumo = 1L;
        Consumo consumoMock = new Consumo();
        Mockito.when(consumoRepository.findById(idConsumo)).thenReturn(Optional.of(consumoMock));

        consumoService.deletarConsumo(idConsumo);
    }

    @Test
    void testPesquisarConsumos() {
        LocalDate dataInicio = LocalDate.of(2023, 1, 1);
        LocalDate dataFim = LocalDate.of(2023, 2, 1);
        Long idPessoa = 1L;
        Long idEndereco = 2L;
        Long idEletrodomestico = 3L;

        List<Consumo> consumosMock = new ArrayList<>();
        Mockito.when(consumoRepository.pesquisarConsumos(dataInicio, dataFim, idPessoa, idEndereco, idEletrodomestico)).thenReturn(consumosMock);

        List<ConsumoDTO> result = consumoService.pesquisarConsumos(dataInicio, dataFim, idPessoa, idEndereco, idEletrodomestico);

        assertNotNull(result);
    }
}