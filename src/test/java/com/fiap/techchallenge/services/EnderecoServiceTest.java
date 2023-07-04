package com.fiap.techchallenge.services;

import com.fiap.techchallenge.dtos.EnderecoDTO;
import com.fiap.techchallenge.entities.Endereco;
import com.fiap.techchallenge.enums.EstadoEnum;
import com.fiap.techchallenge.repositories.EnderecoRepository;
import com.fiap.techchallenge.services.impl.EnderecoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService = new EnderecoServiceImpl();

    @Mock
    private EnderecoRepository enderecoRepository;

    private EnderecoDTO obterEnderecoDTO() {
        EnderecoDTO enderecoDTO = new EnderecoDTO();

        enderecoDTO.setRua("Rua");
        enderecoDTO.setNumero(123);
        enderecoDTO.setBairro("Teste");
        enderecoDTO.setCidade("Teste");
        enderecoDTO.setEstado(EstadoEnum.SP);

        return enderecoDTO;
    }

    @Test
    void cadastrarEndereco() {
        when(enderecoRepository.save(Mockito.any())).thenReturn(new Endereco("Rua", 123, "Teste", "Teste", EstadoEnum.SP));

        EnderecoDTO enderecoDTO = enderecoService.cadastrarEndereco(obterEnderecoDTO());

        assertNotNull(enderecoDTO);
    }
}