package com.fiap.techchallenge.config;

import com.fiap.techchallenge.dtos.ErroDeFormularioDTO;
import com.fiap.techchallenge.exceptions.PessoaExisteException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Component
public class ExceptionHandlerConfig {

    @ExceptionHandler(PessoaExisteException.class)
    public ResponseEntity<Error> handlePessoaException(PessoaExisteException ex) {
        log.error("Erro na requisição, erro: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroDeFormularioDTO>> handleException(MethodArgumentNotValidException ex) {
        log.error("Erro na requisição, erro: ", ex);

        List<ErroDeFormularioDTO> dto = new ArrayList<>();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            ErroDeFormularioDTO erro = new ErroDeFormularioDTO(e.getField(), e.getDefaultMessage());
            dto.add(erro);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex){
        log.error("Erro na requisição, erro: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocorreu um erro inesperado");
    }

    public static class Error {
        private String mensagem;

        public Error(String mensagem) {
            this.mensagem = mensagem;
        }
        public String getMensagem() {
            return mensagem;
        }
        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }
    }

}
