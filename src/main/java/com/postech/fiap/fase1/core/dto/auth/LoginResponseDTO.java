package com.postech.fiap.fase1.core.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    @Schema(description = "Token de autenticação do usuário.",
            example = "eyJhbGciOiJIUzI1NiJ9")
    private String token;

}
