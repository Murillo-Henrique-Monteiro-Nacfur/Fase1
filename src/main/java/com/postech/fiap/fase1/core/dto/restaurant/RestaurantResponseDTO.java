package com.postech.fiap.fase1.core.dto.restaurant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RestaurantResponseDTO {
    @Schema(description = "Identificador único do Restaurante.",
            example = "101")
    private Long id;
    @Schema(description = "Nome do Restaurante.",
            example = "Bar do Zé")
    private String name;

    @Schema(description = "Descricao do Restaurante.",
            example = "Restaurante especializado em comida caseira.")
    private String description;

    @Schema(description = "Tipo de comida do Restaurante.",
            example = "Comida Caseira")
    private String cuisineType;

    @Schema(description = "CNPJ do Restaurante.",
            example = "99999999999999")
    private String cnpj;

    @Schema(description = "Horário em que o restaurante abre.",
            example = "08:00:00")
    private LocalTime openTime;

    @Schema(description = "Horário em que o restaurante fecha.",
            example = "23:00:00")
    private LocalTime closeTime;
}
