package com.postech.fiap.fase1.core.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
    @Schema(description = "Login do usuário.",
            example = "admin")
    @NotBlank
    private String login;
    @Schema(description = "Senha do usuário.",
            example = "adminPassword")
    @NotBlank
    private String password;

}
