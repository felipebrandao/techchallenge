package com.fiap.techchallenge.mappers;

import com.fiap.techchallenge.dtos.EletrodomesticoDTO;
import com.fiap.techchallenge.entities.Eletrodomestico;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EletrodomesticoMapper {
    Eletrodomestico toEntity(EletrodomesticoDTO eletrodomesticoDTO);
    EletrodomesticoDTO toDTO(Eletrodomestico eletrodomestico);
}
