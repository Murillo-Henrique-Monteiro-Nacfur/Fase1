package com.postech.fiap.fase1.webapi.infrastructure.exception;

import com.postech.fiap.fase1.webapi.infrastructure.exception.response.ExceptionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomizedExceptionHandlerTest {

    @InjectMocks
    private CustomizedExceptionHandler exceptionHandler;

    @Mock
    private WebRequest webRequest;

    @Mock
    private BindingResult bindingResult;

    private MethodParameter methodParameter;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        methodParameter = new MethodParameter(this.getClass().getDeclaredMethod("dummyMethod"), -1);
    }

    private void dummyMethod() {
        //meotod vazio para teste
    }

    @Test
    @DisplayName("Deve tratar MethodArgumentNotValidException e retornar resposta com erros de campo")
    void handleMethodArgumentNotValid_shouldReturnResponseWithFieldErrors() {
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

        FieldError fieldError1 = new FieldError("objectName", "email", "não pode ser nulo");
        FieldError fieldError2 = new FieldError("objectName", "password", "deve ter no mínimo 6 caracteres");

        when(ex.getAllErrors()).thenReturn(List.of(fieldError1, fieldError2));
        when(webRequest.getDescription(false)).thenReturn("uri=/test/path");

        ResponseEntity<Object> responseEntity = exceptionHandler.handleMethodArgumentNotValid(
                ex, new HttpHeaders(), HttpStatusCode.valueOf(400), webRequest
        );

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        // Verifica o corpo da resposta
        ExceptionResponse<ExceptionResponse.Fields> responseBody = (ExceptionResponse<ExceptionResponse.Fields>) responseEntity.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getMessage()).isEqualTo("Validation failed for one or more argument");
        assertThat(responseBody.getDetails()).isEqualTo("uri=/test/path");

        // Verifica a lista de campos com erro
        assertThat(responseBody.getFields()).isNotNull().hasSize(2);
        assertThat(responseBody.getFields())
                .extracting(ExceptionResponse.Fields::getField, ExceptionResponse.Fields::getValidation)
                .containsExactlyInAnyOrder(
                        org.assertj.core.api.Assertions.tuple("email", "não pode ser nulo"),
                        org.assertj.core.api.Assertions.tuple("password", "deve ter no mínimo 6 caracteres")
                );
    }

    @Test
    @DisplayName("Deve tratar ApplicationException e retornar a resposta e o status corretos")
    void handleAllExceptions_forApplicationException_shouldReturnCorrectStatusAndMessage() {
        String errorMessage = "Usuário não encontrado com o ID fornecido";
        ApplicationException ex = new ApplicationException(errorMessage, HttpStatus.NOT_FOUND);

        ResponseEntity<ExceptionResponse<Object>> responseEntity = exceptionHandler.handleAllExceptions(ex);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ExceptionResponse<Object> responseBody = responseEntity.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getMessage()).isEqualTo("application error");
        assertThat(responseBody.getDetails()).isEqualTo(errorMessage);
        assertThat(responseBody.getFields()).isNull();
    }

    @Test
    @DisplayName("Deve tratar DataIntegrityViolationException e retornar erro interno genérico")
    void handleAllExceptions_forDataIntegrityViolation_shouldReturnInternalServerError() {
        DataIntegrityViolationException ex = org.mockito.Mockito.mock(DataIntegrityViolationException.class);
        Throwable rootCause = new Throwable("Detalhe do erro de violação de chave única");
        Throwable immediateCause = new Throwable("Causa imediata", rootCause);
        when(ex.getCause()).thenReturn(immediateCause);

        ResponseEntity<ExceptionResponse<Object>> responseEntity = exceptionHandler.handleAllExceptions(ex);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        ExceptionResponse<Object> responseBody = responseEntity.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getMessage()).isEqualTo("Internal error");
        assertThat(responseBody.getDetails()).isEqualTo("DataBase error");
    }
    @Test
    @DisplayName("Deve tratar HttpServerErrorException e retornar erro interno com a mensagem da causa raiz")
    void handleAllExceptions_forHttpServerError_shouldReturnInternalServerError() {
        HttpServerErrorException.InternalServerError ex = org.mockito.Mockito.mock(HttpServerErrorException.InternalServerError.class);
        Throwable rootCause = new Throwable("A mensagem de erro da causa raiz");
        Throwable immediateCause = new Throwable("Causa imediata", rootCause);

        when(ex.getCause()).thenReturn(immediateCause);

        ResponseEntity<ExceptionResponse<Object>> responseEntity = exceptionHandler.handleAllExceptions(ex);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        ExceptionResponse<Object> responseBody = responseEntity.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getMessage()).isEqualTo("exception error");
        assertThat(responseBody.getDetails()).isEqualTo("A mensagem de erro da causa raiz");
        assertThat(responseBody.getTimestamp()).isNotBlank();
    }
}