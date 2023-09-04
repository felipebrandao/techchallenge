package com.fiap.techchallenge.config;

import com.fiap.techchallenge.dtos.ErroDeFormularioDTO;
import com.fiap.techchallenge.exceptions.TechChallengeException;
import com.fiap.techchallenge.exceptions.TechChallengeNaoEncotradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Component
public class ExceptionHandlerConfig {

    @ExceptionHandler(TechChallengeException.class)
    public ResponseEntity<ErrorDTO> handleTechChallengeException(TechChallengeException ex) {
        log.error("Erro na requisição, erro: ", ex);
        ErrorDTO error = new ErrorDTO(Instant.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(TechChallengeNaoEncotradoException.class)
    public ResponseEntity<ErrorDTO> handleTechChallengeException(TechChallengeNaoEncotradoException ex) {
        log.error("Erro na requisição, erro: ", ex);
        ErrorDTO error = new ErrorDTO(Instant.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
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
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDTO> handleException(Exception ex) {
        log.error("Erro na requisição, erro: ", ex);
        ErrorDTO error = new ErrorDTO(Instant.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro inesperado");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    public static class ErrorDTO {
        private Instant timestamp;
        private int status;
        private String mensagem;

        public ErrorDTO(Instant timestamp, int status, String mensagem) {
            this.timestamp = timestamp;
            this.status = status;
            this.mensagem = mensagem;
        }

        public Instant getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Instant timestamp) {
            this.timestamp = timestamp;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMensagem() {
            return mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }
    }
}
