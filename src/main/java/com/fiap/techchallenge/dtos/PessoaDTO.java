package com.fiap.techchallenge.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.techchallenge.entities.Pessoa;
import com.fiap.techchallenge.enums.SexoEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class PessoaDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty
    @NotBlank(message = "Nome da Pessoa é um campo obrigatório e não pode estar em branco")
    private String nome;

    @JsonProperty
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeNascimento;

    @JsonProperty
    private SexoEnum sexo;

    @JsonProperty
    @CPF
    @NotBlank(message = "CPF da Pessoa é um campo obrigatório e não pode estar em branco")
    private String cpf;

    public PessoaDTO(Pessoa pessoa) {
        this.id = pessoa.getId();
        this.nome = pessoa.getNome();
        this.dataDeNascimento = pessoa.getDataDeNascimento();
        this.sexo = pessoa.getSexo();
        this.cpf = pessoa.getCpf();
    }

    public Pessoa toEntity() {
        return new Pessoa(this.nome, this.dataDeNascimento, this.sexo, this.cpf);
    }
}
