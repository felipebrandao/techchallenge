package com.fiap.techchallenge.exceptions;

public class PessoaExisteException extends RuntimeException {

    public PessoaExisteException(String mensagem) {
        super(mensagem);
    }
}
