package com.fiap.techchallenge.mappers;

import com.fiap.techchallenge.dtos.MoradorDTO;
import com.fiap.techchallenge.entities.Morador;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MoradorMapper {

    MoradorDTO toDTO(Morador morador);
    Morador toEntity(MoradorDTO moradorDTO);

    List<MoradorDTO> toDTOList(List<Morador> moradores);
}
