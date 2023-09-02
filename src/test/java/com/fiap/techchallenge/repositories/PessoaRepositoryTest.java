package com.fiap.techchallenge.repositories;

import com.fiap.techchallenge.entities.Pessoa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PessoaRepositoryTest {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Rollback(false)
    public void testExistsByCpf() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste");
        pessoa.setCpf("12345678901");
        entityManager.persist(pessoa);

        boolean exists = pessoaRepository.existsByCpf(pessoa.getCpf());
        assertTrue(exists);
    }

    @Test
    public void testFindByIdAndCPF() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste");
        pessoa.setCpf("12345678901");
        entityManager.persist(pessoa);

        Pessoa foundPessoa = pessoaRepository.findByIdAndCPF(pessoa.getId(), pessoa.getCpf());
        assertNotNull(foundPessoa);
        assertEquals(pessoa.getId(), foundPessoa.getId());
        assertEquals(pessoa.getCpf(), foundPessoa.getCpf());
    }

    @Test
    public void testFindByNomeAndCpf() {
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setNome("Maria");
        pessoa1.setCpf("12345678901");
        entityManager.persist(pessoa1);

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNome("João");
        pessoa2.setCpf("98765432101");
        entityManager.persist(pessoa2);

        Pessoa pessoa3 = new Pessoa();
        pessoa3.setNome("Maria");
        pessoa3.setCpf("54321678901");
        entityManager.persist(pessoa3);

        List<Pessoa> pessoas = pessoaRepository.findByNomeAndCpf("Maria", null);
        assertEquals(2, pessoas.size());

        pessoas = pessoaRepository.findByNomeAndCpf("Maria", "54321678901");
        assertEquals(1, pessoas.size());
        assertEquals("54321678901", pessoas.get(0).getCpf());

        pessoas = pessoaRepository.findByNomeAndCpf("João", "12345678901");
        assertEquals(0, pessoas.size());
    }
}
