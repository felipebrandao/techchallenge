package com.fiap.techchallenge.morador.repositories;

import com.fiap.techchallenge.morador.entities.Morador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class MoradorRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MoradorRepository moradorRepository;

    @BeforeEach
    public void setUp() {
        entityManager.clear();
    }

    @Test
    public void testIsExistePessoaCadastradaNoEndereco() {
        Morador morador1 = new Morador();
        morador1.setIdEndereco(1L);
        morador1.setIdPessoa(1L);
        morador1 = entityManager.merge(morador1);

        boolean existe = moradorRepository.isExistePessoaCadastradaNoEndereco(1L, 1L);
        assertTrue(existe);
    }

    @Test
    public void testPesquisarMoradores() {
        Morador morador1 = new Morador();
        morador1.setIdEndereco(1L);
        morador1.setIdPessoa(1L);
        morador1 = entityManager.merge(morador1);

        Morador morador2 = new Morador();
        morador2.setIdEndereco(1L);
        morador2.setIdPessoa(2L);
        morador2 = entityManager.merge(morador2);

        List<Morador> moradores = moradorRepository.pesquisarMoradores(null, 1L);

        assertEquals(2, moradores.size());
    }
}

