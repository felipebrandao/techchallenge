package com.fiap.techchallenge.enums;

public enum SexoEnum {
    MASCULINO("M"),
    FEMININO("F");

    private String sexo;

    private SexoEnum(String sexo) {
        this.sexo = sexo;
    }

    public String getSexo() {
        return sexo;
    }
}
