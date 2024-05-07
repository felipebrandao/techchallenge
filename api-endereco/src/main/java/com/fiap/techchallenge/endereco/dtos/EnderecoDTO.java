package com.fiap.techchallenge.endereco.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.techchallenge.endereco.entities.Endereco;
import com.fiap.techchallenge.endereco.enums.EstadoEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EnderecoDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty
    @NotBlank(message = "Rua do Endereço é um campo obrigatório e não pode estar em branco")
    private String rua;

    @JsonProperty
    private Integer numero;

    @JsonProperty
    @NotBlank(message = "Bairro do Endereço é um campo obrigatório e não pode estar em branco")
    private String bairro;

    @JsonProperty
    @NotBlank(message = "Cidade do Endereço é um campo obrigatório e não pode estar em branco")
    private String cidade;

    @JsonProperty
    private EstadoEnum estado;

    public EnderecoDTO(Endereco endereco) {
        this.id = endereco.getId();
        this.rua = endereco.getRua();
        this.numero = endereco.getNumero();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade();
        this.estado = endereco.getEstado();
    }

    @JsonIgnore
    public boolean isPeloMenosUmCampoPreenchido() {
        return getRua() != null && !getRua().isEmpty()
                || getBairro() != null && !getBairro().isEmpty()
                || getCidade() != null && !getCidade().isEmpty()
                || getEstado() != null;
    }
}
