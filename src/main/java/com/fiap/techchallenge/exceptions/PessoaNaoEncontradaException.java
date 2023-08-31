package com.fiap.techchallenge.exceptions;

public class PessoaNaoEncontradaException extends RuntimeException {
    public PessoaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
