package com.fiap.techchallenge.endereco.dtos;

import java.time.Instant;

public class ErrorDTO {

    private Instant timestamp;
    private Integer status;
    private String error;

    public ErrorDTO(Instant timestamp, Integer status, String error) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
