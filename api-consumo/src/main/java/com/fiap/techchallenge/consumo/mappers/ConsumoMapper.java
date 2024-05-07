package com.fiap.techchallenge.consumo.mappers;

import com.fiap.techchallenge.consumo.dtos.ConsumoDTO;
import com.fiap.techchallenge.consumo.entities.Consumo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConsumoMapper {

    ConsumoDTO toDTO(Consumo consumo);
    Consumo toEntity(ConsumoDTO consumoDTO);

    List<ConsumoDTO> toDTOList(List<Consumo> consumos);
}
