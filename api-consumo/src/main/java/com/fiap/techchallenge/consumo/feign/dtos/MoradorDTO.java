package com.fiap.techchallenge.consumo.feign.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoradorDTO {

    private Long id;
    private String parentesco;
    private Long idPessoa;
    private Long idEndereco;
}
