package com.fiap.techchallenge.morador.feign.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO  {

    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private String sexo;
    private String cpf;
}
