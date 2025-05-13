package com.postech.fiap.fase1.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
public class UserRequestUpdatePasswordDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordConfirmation;
}
