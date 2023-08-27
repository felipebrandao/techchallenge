package com.fiap.techchallenge.exceptions;

public class EnderecoNaoEncontradoException extends RuntimeException {
    public EnderecoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
