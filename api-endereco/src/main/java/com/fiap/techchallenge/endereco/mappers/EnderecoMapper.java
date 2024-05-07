package com.fiap.techchallenge.endereco.mappers;

import com.fiap.techchallenge.endereco.dtos.EnderecoDTO;
import com.fiap.techchallenge.endereco.entities.Endereco;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    Endereco toEntity(EnderecoDTO enderecoDTO);
    EnderecoDTO toDTO(Endereco endereco);
}
