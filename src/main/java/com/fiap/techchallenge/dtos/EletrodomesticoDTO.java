package com.fiap.techchallenge.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EletrodomesticoDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty
    @NotBlank(message = "Nome do Eletrodoméstico é um campo obrigatório e não pode estar em branco")
    private String nome;

    @JsonProperty
    @NotBlank(message = "Modelo do Eletrodoméstico é um campo obrigatório e não pode estar em branco")
    private String modelo;

    @JsonProperty
    @PositiveOrZero
    private Double potencia;

    public EletrodomesticoDTO(Long id, String nome, String modelo, Double potencia) {
        this.id = id;
        this.nome = nome;
        this.modelo = modelo;
        this.potencia = potencia;
    }

    @JsonIgnore
    public boolean isPeloMenosUmCampoPreenchido() {
        return getNome() != null && !getNome().isEmpty()
                || getModelo() != null && !getModelo().isEmpty()
                || getPotencia() != null;
    }
}
