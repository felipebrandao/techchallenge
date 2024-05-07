package com.fiap.techchallenge.endereco.repositories;

import com.fiap.techchallenge.endereco.entities.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;

import static com.fiap.techchallenge.endereco.enums.EstadoEnum.SP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
class EnderecoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @BeforeEach
    public void setUp() {
        entityManager.clear();
    }

    @Test
    void testBuscarEnderecosPorFiltros() {

        Endereco endereco1 = criarEndereco("Rua Teste 1", "Bairro 1", "Cidade 1");
        Endereco endereco2 = criarEndereco("Rua Teste 2", "Bairro 2", "Cidade 2");
        Endereco endereco3 = criarEndereco("Rua Outra", "Bairro 1", "Cidade 3");

        enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2, endereco3));

        List<Endereco> resultados = enderecoRepository.buscarEnderecosPorFiltros("Rua Teste", "Bairro 1", "Cidade 1");

        assertEquals(1, resultados.size());
        assertEquals("Rua Teste 1", resultados.get(0).getRua());
        assertEquals("Bairro 1", resultados.get(0).getBairro());
        assertEquals("Cidade 1", resultados.get(0).getCidade());
    }

    @Test
    public void testIsExisteEnderecoCadastrado() {

        Endereco endereco = new Endereco();
        endereco.setRua("Rua Teste");
        endereco.setBairro("Bairro Teste");
        endereco.setCidade("Cidade Teste");
        endereco.setEstado(SP);
        endereco.setNumero(123);

        enderecoRepository.save(endereco);


        boolean result = enderecoRepository.isExisteEnderecoCadastrado(
                "Rua Teste",
                123,
                "Bairro Teste",
                "Cidade Teste",
                SP
        );

        assertTrue(result);
    }

    private Endereco criarEndereco(String rua, String bairro, String cidade) {
        Endereco endereco = new Endereco();
        endereco.setRua(rua);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        endereco.setEstado(SP);
        return endereco;
    }
}
