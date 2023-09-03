package com.fiap.techchallenge.repositories;

import com.fiap.techchallenge.entities.*;
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

        Eletrodomestico eletrodomestico = new Eletrodomestico();
        eletrodomestico.setNome("Eletrodomestico 1");
        eletrodomestico = entityManager.merge(eletrodomestico);

        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Pessoa 1");
        pessoa = entityManager.merge(pessoa);

        Endereco endereco = new Endereco();
        endereco.setRua("Endereco 1");
        endereco = entityManager.merge(endereco);

        Morador morador = new Morador();
        morador.setIdPessoa(pessoa.getId());
        morador.setIdEndereco(endereco.getId());
        morador = entityManager.merge(morador);

        Consumo consumo = new Consumo();
        consumo.setIdMorador(morador.getId());
        consumo.setIdEletrodomestico(eletrodomestico.getId());
        consumo.setDataConsumo(dataInicio);
        consumo.setTempoConsumo(Double.valueOf(3));
        consumo = entityManager.merge(consumo);

        List<Consumo> result = consumoRepository.pesquisarConsumos(dataInicio, dataFim, null, null, null);

        assertEquals(1, result.size());
        assertEquals(consumo.getDataConsumo(), result.get(0).getDataConsumo());
    }
}
