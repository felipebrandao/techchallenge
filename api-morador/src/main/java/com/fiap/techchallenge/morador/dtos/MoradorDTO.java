package com.fiap.techchallenge.morador.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
public class MoradorDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty
    @NotBlank(message = "Parentesco é um campo obrigatório e não pode estar em branco")
    private String parentesco;

    @JsonProperty
    @NotNull(message = "IdPessoa é um campo obrigatório")
    private Long idPessoa;

    @JsonProperty
    @NotNull(message = "IdEndereco é um campo obrigatório")
    private Long idEndereco;

}
