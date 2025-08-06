package com.postech.fiap.fase1.webapi.infrastructure.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationExceptionTest {

    @Test
    @DisplayName("Deve criar uma exceção com mensagem e status HTTP definidos")
    void shouldCreateExceptionWithMessageAndStatus() {
        String errorMessage = "Recurso não encontrado";
        HttpStatus status = HttpStatus.NOT_FOUND;

        ApplicationException exception = new ApplicationException(errorMessage, status);

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
        assertThat(exception.getHttpStatus()).isEqualTo(status);
        assertThat(exception.getErrorCause()).isNull();
    }

    @Test
    @DisplayName("Deve criar uma exceção com mensagem, causa do erro e status HTTP definidos")
    void shouldCreateExceptionWithMessageCauseAndStatus() {
        String errorMessage = "Erro de validação";
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        Map<String, String> errorDetails = Map.of("field", "email", "error", "formato inválido");

        ApplicationException exception = new ApplicationException(errorMessage, errorDetails, status);

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
        assertThat(exception.getHttpStatus()).isEqualTo(status);
        assertThat(exception.getErrorCause()).isNotNull().isEqualTo(errorDetails);
    }

    @Test
    @DisplayName("Deve criar uma exceção com mensagem e usar BAD_REQUEST como status HTTP padrão")
    void shouldCreateExceptionWithDefaultBadRequestStatus() {
        String errorMessage = "Parâmetro de requisição inválido";

        ApplicationException exception = new ApplicationException(errorMessage);

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
        assertThat(exception.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getErrorCause()).isNull();
    }
}