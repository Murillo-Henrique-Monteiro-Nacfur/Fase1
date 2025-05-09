package com.postech.fiap.fase1.configuration.exception.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ExceptionResponse<T> implements Serializable {
    private String timestamp;
    private String message;
    private String details;
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private transient List<T> fields;

    public ExceptionResponse(String timestamp, String message, String details, String status) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.status = status;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class Fields {
        private String field;
        private String validation;
    }

}