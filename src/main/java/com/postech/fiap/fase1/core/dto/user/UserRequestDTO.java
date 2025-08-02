package com.postech.fiap.fase1.core.dto.user;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class UserRequestDTO {

    @Schema(description = "Nome do usuário.",
            example = "José")
    @NotBlank
    private String name;

    @Schema(description = "Email do usuário.",
            example = "jose@gmail.com")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "Login do usuário.",
            example = "jose123")
    @NotBlank
    private String login;
    @Schema(description = "Senha do usuário.",
            example = "senha123")
    @NotBlank
    private String password;
    @Schema(description = "Confirmação da senha do usuário.",
            example = "senha123")
    @NotBlank
    private String passwordConfirmation;
    @Schema(description = "Data de nascimento do usuário.",
            example = "2025-05-06")
    @NotNull
    private LocalDate birthDate;
}
