package com.fiap.techchallenge.morador.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErroDeFormularioDTO {

    private String campo;
    private String erro;

}
