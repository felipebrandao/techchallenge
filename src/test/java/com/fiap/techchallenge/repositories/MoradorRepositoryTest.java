package com.fiap.techchallenge.repositories;

import com.fiap.techchallenge.entities.Endereco;
import com.fiap.techchallenge.entities.Morador;
import com.fiap.techchallenge.entities.Pessoa;
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
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Pessoa 1");
        pessoa = entityManager.merge(pessoa);

        Endereco endereco = new Endereco();
        endereco.setRua("Endereco 1");
        endereco = entityManager.merge(endereco);

        Morador morador1 = new Morador();
        morador1.setIdEndereco(endereco.getId());
        morador1.setIdPessoa(pessoa.getId());
        morador1 = entityManager.merge(morador1);

        boolean existe = moradorRepository.isExistePessoaCadastradaNoEndereco(endereco.getId(), pessoa.getId());
        assertTrue(existe);
    }

    @Test
    public void testPesquisarMoradores() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Pessoa 1");
        pessoa = entityManager.merge(pessoa);

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNome("Pessoa 2");
        pessoa2 = entityManager.merge(pessoa2);

        Endereco endereco = new Endereco();
        endereco.setRua("Endereco 1");
        endereco = entityManager.merge(endereco);

        Morador morador1 = new Morador();
        morador1.setIdEndereco(endereco.getId());
        morador1.setIdPessoa(pessoa.getId());
        morador1 = entityManager.merge(morador1);

        Morador morador2 = new Morador();
        morador2.setIdEndereco(endereco.getId());
        morador2.setIdPessoa(pessoa2.getId());
        morador2 = entityManager.merge(morador2);

        List<Morador> moradores = moradorRepository.pesquisarMoradores(null, endereco.getId());

        assertEquals(2, moradores.size());
    }
}

