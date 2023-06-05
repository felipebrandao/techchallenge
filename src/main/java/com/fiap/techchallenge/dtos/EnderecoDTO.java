package com.fiap.techchallenge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fiap.techchallenge.entities.Endereco;
import com.fiap.techchallenge.enums.EstadoEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EnderecoDTO {
    @JsonProperty
    private Long id;
    @JsonProperty
    @NotBlank(message = "Rua do Endereço é um campo obrigatório e não pode estar em branco")
    private String rua;
    @JsonProperty
    //@NotBlank(message = "Número do Endereço é um campo obrigatório e não pode estar em branco")
    private Integer numero;
    @JsonProperty
    @NotBlank(message = "Bairro do Endereço é um campo obrigatório e não pode estar em branco")
    private String bairro;
    @JsonProperty
    @NotBlank(message = "Cidade do Endereço é um campo obrigatório e não pode estar em branco")
    private String cidade;
    @JsonProperty
    //@NotBlank(message = "Estado do Endereço é um campo obrigatório e não pode estar em branco")
    private EstadoEnum estado;

    public EnderecoDTO(Endereco endereco) {
        this.id = endereco.getId();
        this.rua = endereco.getRua();
        this.numero = endereco.getNumero();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade();
        this.estado = endereco.getEstado();
    }

    public Endereco toEntity() {
        return new Endereco(this.rua, this.numero, this.bairro, this.cidade, this.estado);
    }

}
