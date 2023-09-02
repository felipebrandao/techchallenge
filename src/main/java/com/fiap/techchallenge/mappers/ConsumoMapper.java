package com.fiap.techchallenge.mappers;

import com.fiap.techchallenge.dtos.ConsumoDTO;
import com.fiap.techchallenge.entities.Consumo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConsumoMapper {

    ConsumoDTO toDTO(Consumo consumo);
    Consumo toEntity(ConsumoDTO consumoDTO);

    List<ConsumoDTO> toDTOList(List<Consumo> consumos);
}
