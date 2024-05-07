package com.fiap.techchallenge.morador.mappers;

import com.fiap.techchallenge.morador.dtos.MoradorDTO;
import com.fiap.techchallenge.morador.entities.Morador;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MoradorMapper {

    MoradorDTO toDTO(Morador morador);
    Morador toEntity(MoradorDTO moradorDTO);

    List<MoradorDTO> toDTOList(List<Morador> moradores);
}
