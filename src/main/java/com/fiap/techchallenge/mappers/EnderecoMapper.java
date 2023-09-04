package com.fiap.techchallenge.mappers;

import com.fiap.techchallenge.dtos.EnderecoDTO;
import com.fiap.techchallenge.entities.Endereco;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    Endereco toEntity(EnderecoDTO enderecoDTO);
    EnderecoDTO toDTO(Endereco endereco);
}
