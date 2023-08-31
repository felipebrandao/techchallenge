package com.fiap.techchallenge.exceptions;

public class PessoaCamposNaoPreenchidosException extends RuntimeException {
    public PessoaCamposNaoPreenchidosException(String mensagem) {
        super(mensagem);
    }
}
