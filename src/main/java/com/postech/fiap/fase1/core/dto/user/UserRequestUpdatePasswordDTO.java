package com.postech.fiap.fase1.core.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


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
