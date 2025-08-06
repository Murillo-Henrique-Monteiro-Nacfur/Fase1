package com.postech.fiap.fase1.core.dto.restaurant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RestaurantRequestDTO {
    @Schema(description = "Identificador único do Restaurante.",
            example = "101")
    private Long id;
    @Schema(description = "Nome do Restaurante.",
            example = "Bar do Zé")
    @NotBlank
    private String name;

    @Schema(description = "Descricao do Restaurante.",
            example = "Restaurante especializado em comida caseira.")
    @NotBlank
    private String description;

    @Schema(description = "Tipo de comida do Restaurante.",
            example = "Comida Caseira")
    @NotBlank
    private String cuisineType;

    @Schema(description = "CNPJ do Restaurante.",
            example = "99999999999999")
    @NotBlank
    private String cnpj;

    @Schema(description = "Horário em que o restaurante abre.",
            example = "08:00:00")
    @NotNull
    private LocalTime openTime;

    @Schema(description = "Horário em que o restaurante fecha.",
            example = "23:00:00")
    @NotNull
    private LocalTime closeTime;

    @Schema(description = "Usuário administrados do restaurante.")
    @NotNull
    private Long userId;
}
