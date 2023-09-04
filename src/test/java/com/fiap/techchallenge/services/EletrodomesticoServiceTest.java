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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EletrodomesticoServiceTest {

    @Spy
    private EletrodomesticoMapper eletrodomesticoMapper = Mappers.getMapper(EletrodomesticoMapper.class);

    @Mock
    private EletrodomesticoRepository eletrodomesticoRepository;

    @InjectMocks
    private EletrodomesticoService eletrodomesticoService = new EletrodomesticoServiceImpl(
            eletrodomesticoRepository,
            eletrodomesticoMapper
    );

    @Test
    void cadastrarEletrodomestico() {
        when(eletrodomesticoRepository.save(Mockito.any())).thenReturn(new Eletrodomestico("Teste", "Teste", Double.valueOf(1L)));

        EletrodomesticoDTO eletrodomesticoDTO = eletrodomesticoService.cadastrarEletrodomestico(obterEletrodomesticoDTO());

        assertNotNull(eletrodomesticoDTO);
    }

    @Test
    void testAtualizarEletrodomestico() {
        Long idEletrodomestico = 1L;
        EletrodomesticoDTO eletrodomesticoDTO = obterEletrodomesticoDTO();

        Eletrodomestico eletrodomesticoExistente = new Eletrodomestico("Nome Test", "Modelo Test", 50.0);

        when(eletrodomesticoRepository.findById(idEletrodomestico)).thenReturn(java.util.Optional.of(eletrodomesticoExistente));
        when(eletrodomesticoRepository.save(Mockito.any())).thenReturn(eletrodomesticoExistente);

        EletrodomesticoDTO eletrodomesticoAtualizado = eletrodomesticoService.atualizarEletrodomestico(idEletrodomestico, eletrodomesticoDTO);

        assertNotNull(eletrodomesticoAtualizado);
        assertEquals(eletrodomesticoDTO.getNome(), eletrodomesticoAtualizado.getNome());
        assertEquals(eletrodomesticoDTO.getModelo(), eletrodomesticoAtualizado.getModelo());
        assertEquals(eletrodomesticoDTO.getPotencia(), eletrodomesticoAtualizado.getPotencia());
    }

    @Test
    void testDeletarEletrodomestico() {
        Long idEletrodomestico = 1L;
        Eletrodomestico eletrodomesticoMock = new Eletrodomestico();
        when(eletrodomesticoRepository.findById(idEletrodomestico)).thenReturn(java.util.Optional.of(eletrodomesticoMock));

        eletrodomesticoService.deletarEletrodomestico(idEletrodomestico);
    }

    @Test
    void testGetEletrodomesticoById() {
        Long idEletrodomestico = 1L;
        Eletrodomestico eletrodomesticoMock = new Eletrodomestico();
        when(eletrodomesticoRepository.findById(idEletrodomestico)).thenReturn(java.util.Optional.of(eletrodomesticoMock));
        when(eletrodomesticoMapper.toDTO(eletrodomesticoMock)).thenReturn(new EletrodomesticoDTO());

        EletrodomesticoDTO result = eletrodomesticoService.getEletrodomesticoById(idEletrodomestico);

        assertNotNull(result);
    }

    @Test
    void testPesquisarEletrodomesticos() {
        EletrodomesticoDTO dto = new EletrodomesticoDTO();
        dto.setNome("Nome Test");
        dto.setModelo("Modelo Test");
        dto.setPotencia(200.0);

        List<Eletrodomestico> eletrodomesticosMock = new ArrayList<>();
        when(eletrodomesticoRepository.findByNomeOrModeloOrPotencia(dto.getNome(), dto.getModelo(), dto.getPotencia()))
                .thenReturn(eletrodomesticosMock);

        List<EletrodomesticoDTO> result = eletrodomesticoService.pesquisarEletrodomesticos(dto);

        assertNotNull(result);
    }

    private EletrodomesticoDTO obterEletrodomesticoDTO() {
        EletrodomesticoDTO eletrodomesticoDTO = new EletrodomesticoDTO();
        eletrodomesticoDTO.setNome("Teste");
        eletrodomesticoDTO.setModelo("Teste");
        eletrodomesticoDTO.setPotencia(Double.valueOf(1L));
        return eletrodomesticoDTO;
    }
}