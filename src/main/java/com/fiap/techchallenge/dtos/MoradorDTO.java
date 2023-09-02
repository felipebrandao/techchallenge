package com.fiap.techchallenge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
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
