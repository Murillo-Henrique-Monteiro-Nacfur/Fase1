package com.postech.fiap.fase1.configuration.exception;

import com.postech.fiap.fase1.configuration.exception.response.ExceptionResponse;
import jakarta.annotation.Nonnull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@ControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @Nonnull HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<ExceptionResponse.Fields> fieldsList = new ArrayList<>();

        ex.getAllErrors().forEach(error -> {
            String message = error.getDefaultMessage();
            String field = ((FieldError) error).getField();

            ExceptionResponse.Fields exceptionField = ExceptionResponse.Fields.builder().field(field).validation(message).build();

            fieldsList.add(exceptionField);
        });

        ExceptionResponse<ExceptionResponse.Fields> exceptionResponse = new ExceptionResponse<>(
                LocalDateTime.now().toString()
                , "Validation failed for one or more argument"
                , request.getDescription(false)
                , status.toString()
        );

        exceptionResponse.setFields(fieldsList);
        return super.handleExceptionInternal(ex, exceptionResponse, headers, status, request);
    }

    @ExceptionHandler(ApplicationException.class)
    public final ResponseEntity<ExceptionResponse<Object>> handleAllExceptions(ApplicationException applicationException) {
        ExceptionResponse<Object> exceptionResponse = new ExceptionResponse<>(
                LocalDateTime.now().toString()
                , "application error"
                , applicationException.getMessage()
                , applicationException.getHttpStatus().toString()
        );

        return new ResponseEntity<>(exceptionResponse, applicationException.getHttpStatus());
    }

    @ExceptionHandler(value = {InvalidDataAccessResourceUsageException.class, DataIntegrityViolationException.class})
    public final ResponseEntity<ExceptionResponse<Object>> handleAllExceptions(NonTransientDataAccessException dataIntegrityViolationException) {
        ExceptionResponse<Object> exceptionResponse = new ExceptionResponse<>(
                LocalDateTime.now().toString()
                , "DataBase Integrity error"
                , dataIntegrityViolationException.getCause().getCause().getMessage()
                , HttpStatus.BAD_REQUEST.toString()
        );

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public final ResponseEntity<ExceptionResponse<Object>> handleAllExceptions(HttpServerErrorException.InternalServerError dataIntegrityViolationException) {
        ExceptionResponse<Object> exceptionResponse = new ExceptionResponse<>(
                LocalDateTime.now().toString()
                , "exception error"
                , dataIntegrityViolationException.getCause().getCause().getMessage()
                , HttpStatus.INTERNAL_SERVER_ERROR.toString()
        );

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
