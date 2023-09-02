package com.fiap.techchallenge.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ConsumoDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long idConsumo;

    @JsonProperty
    @NotNull(message = "IdPessoa é um campo obrigatório")
    private Long idMorador;

    @JsonProperty
    @NotNull(message = "IdPessoa é um campo obrigatório")
    private Long idEletrodomestico;

    @JsonProperty
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataConsumo;

    @JsonProperty
    @NotNull(message = "TempoConsumo é um campo obrigatório")
    private Double tempoConsumo;
}
