package com.postech.fiap.fase1.webapi.infrastructure.exception.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionResponseTest {

    @Test
    @DisplayName("Deve criar uma instância de ExceptionResponse usando o construtor de 3 argumentos")
    void shouldCreateInstanceWithThreeArgsConstructor() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        String message = "Erro de validação";
        String details = "Ocorreu um erro ao processar a requisição";

        ExceptionResponse<Object> response = new ExceptionResponse<>(timestamp, message, details);

        assertThat(response).isNotNull();
        assertThat(response.getTimestamp()).isEqualTo(timestamp);
        assertThat(response.getMessage()).isEqualTo(message);
        assertThat(response.getDetails()).isEqualTo(details);
        assertThat(response.getFields()).isNull();
    }

    @Test
    @DisplayName("Deve criar uma instância de ExceptionResponse usando o Builder")
    void shouldCreateInstanceWithBuilder() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        ExceptionResponse.Fields fieldError = ExceptionResponse.Fields.builder()
                .field("email")
                .validation("não pode ser nulo")
                .build();

        // Act
        ExceptionResponse<ExceptionResponse.Fields> response = ExceptionResponse.<ExceptionResponse.Fields>builder()
                .timestamp(timestamp)
                .message("Erro de validação de campo")
                .details("Um ou mais campos falharam na validação")
                .fields(List.of(fieldError))
                .build();

        assertThat(response).isNotNull();
        assertThat(response.getTimestamp()).isEqualTo(timestamp);
        assertThat(response.getMessage()).isEqualTo("Erro de validação de campo");
        assertThat(response.getDetails()).isEqualTo("Um ou mais campos falharam na validação");

        assertThat(response.getFields()).isNotNull().hasSize(1);
        assertThat(response.getFields().getFirst().getField()).isEqualTo("email");
        assertThat(response.getFields().getFirst().getValidation()).isEqualTo("não pode ser nulo");
    }

    @Test
    @DisplayName("Deve criar uma instância da classe aninhada Fields corretamente")
    void shouldCreateNestedFieldsInstanceCorrectly() {
        // Arrange
        String fieldName = "username";
        String validationMessage = "deve ter no mínimo 5 caracteres";

        ExceptionResponse.Fields fieldError = new ExceptionResponse.Fields(fieldName, validationMessage);

        assertThat(fieldError).isNotNull();
        assertThat(fieldError.getField()).isEqualTo(fieldName);
        assertThat(fieldError.getValidation()).isEqualTo(validationMessage);
    }

    @Test
    @DisplayName("Deve permitir a modificação de campos através dos setters")
    void shouldAllowModificationViaSetters() {
        String timestamp = LocalDateTime.now().toString();
        ExceptionResponse<Object> response = new ExceptionResponse<>(timestamp, "Mensagem antiga", "Detalhe antigo");

        String newMessage = "Mensagem nova";
        String newDetails = "Detalhe novo";

        response.setMessage(newMessage);
        response.setDetails(newDetails);

        assertThat(response.getMessage()).isEqualTo(newMessage);
        assertThat(response.getDetails()).isEqualTo(newDetails);
        assertThat(response.getTimestamp()).isEqualTo(timestamp);
    }
}