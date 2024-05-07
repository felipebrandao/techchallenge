package com.fiap.techchallenge.consumo.repositories;

import com.fiap.techchallenge.consumo.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ConsumoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ConsumoRepository consumoRepository;

    @BeforeEach
    public void setUp() {
        entityManager.clear();
    }

    @Test
    public void testPesquisarConsumos() {
        LocalDate dataInicio = LocalDate.of(2023, 1, 1);
        LocalDate dataFim = LocalDate.of(2023, 2, 1);

        Consumo consumo = new Consumo();
        consumo.setIdMorador(1L);
        consumo.setIdEletrodomestico(1L);
        consumo.setDataConsumo(dataInicio);
        consumo.setTempoConsumo(Double.valueOf(3));
        consumo = entityManager.merge(consumo);

        List<Consumo> result = consumoRepository.pesquisarConsumos(dataInicio, dataFim, null, null);

        assertEquals(1, result.size());
        assertEquals(consumo.getDataConsumo(), result.get(0).getDataConsumo());
    }
}
