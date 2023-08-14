package com.fiap.techchallenge.mappers;

import com.fiap.techchallenge.dtos.PessoaDTO;
import com.fiap.techchallenge.entities.Pessoa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    Pessoa toEntity(PessoaDTO pessoaDTO);
    PessoaDTO toDTO(Pessoa pessoa);
}
