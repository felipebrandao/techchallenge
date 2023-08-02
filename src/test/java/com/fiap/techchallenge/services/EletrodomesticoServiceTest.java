package com.fiap.techchallenge.services;

import com.fiap.techchallenge.dtos.EletrodomesticoDTO;
import com.fiap.techchallenge.entities.Eletrodomestico;
import com.fiap.techchallenge.mappers.EletrodomesticoMapper;
import com.fiap.techchallenge.repositories.EletrodomesticoRepository;
import com.fiap.techchallenge.services.impl.EletrodomesticoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EletrodomesticoServiceTest {

    @InjectMocks
    private EletrodomesticoService eletrodomesticoService = new EletrodomesticoServiceImpl();
    @Spy
    private EletrodomesticoMapper eletrodomesticoMapper = Mappers.getMapper(EletrodomesticoMapper.class);

    @Mock
    private EletrodomesticoRepository eletrodomesticoRepository;

    private EletrodomesticoDTO obterEletrodomesticoDTO() {
        EletrodomesticoDTO eletrodomesticoDTO = new EletrodomesticoDTO();

        eletrodomesticoDTO.setNome("Teste");
        eletrodomesticoDTO.setModelo("Teste");
        eletrodomesticoDTO.setPotencia(Double.valueOf(1L));

        return eletrodomesticoDTO;
    }

    @Test
    void cadastrarEndereco() {
        when(eletrodomesticoRepository.save(Mockito.any())).thenReturn(new Eletrodomestico("Teste", "Teste", Double.valueOf(1L)));

        EletrodomesticoDTO eletrodomesticoDTO = eletrodomesticoService.cadastrarEndereco(obterEletrodomesticoDTO());

        assertNotNull(eletrodomesticoDTO);
    }
}