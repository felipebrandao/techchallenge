package com.fiap.techchallenge.eletrodomestico.repositories;

import com.fiap.techchallenge.eletrodomestico.entities.Eletrodomestico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class EletrodomesticoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EletrodomesticoRepository eletrodomesticoRepository;

    @BeforeEach
    public void setUp() {
        entityManager.clear();
    }

    @Test
    public void testPesquisarEletrodomesticos(){
        Eletrodomestico eletrodomestico1 = new Eletrodomestico();
        eletrodomestico1.setNome("Eletrodomestico 1");
        eletrodomestico1 = entityManager.merge(eletrodomestico1);

        Eletrodomestico eletrodomestico2 = new Eletrodomestico();
        eletrodomestico2.setNome("Eletrodomestico 2");
        eletrodomestico2 = entityManager.merge(eletrodomestico2);

        List<Eletrodomestico> result = eletrodomesticoRepository.pesquisarEletrodomesticos("Eletrodomestico 2", null,null);

        assertEquals(1, result.size());
        assertEquals(eletrodomestico2.getNome(), result.get(0).getNome());
    }

}
