package com.fiap.techchallenge.pessoa.mappers;

import com.fiap.techchallenge.pessoa.dtos.PessoaDTO;
import com.fiap.techchallenge.pessoa.entities.Pessoa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    Pessoa toEntity(PessoaDTO pessoaDTO);
    PessoaDTO toDTO(Pessoa pessoa);
}
