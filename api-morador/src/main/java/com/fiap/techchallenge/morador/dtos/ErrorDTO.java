package com.fiap.techchallenge.morador.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ErrorDTO {

    private Instant timestamp;
    private int status;
    private String mensagem;

}
