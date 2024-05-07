package com.fiap.techchallenge.eletrodomestico.mappers;

import com.fiap.techchallenge.eletrodomestico.dtos.EletrodomesticoDTO;
import com.fiap.techchallenge.eletrodomestico.entities.Eletrodomestico;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EletrodomesticoMapper {
    Eletrodomestico toEntity(EletrodomesticoDTO eletrodomesticoDTO);
    EletrodomesticoDTO toDTO(Eletrodomestico eletrodomestico);
    List<EletrodomesticoDTO> toDTOList(List<Eletrodomestico> eletrodomesticos);
}
