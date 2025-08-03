package com.postech.fiap.fase1.core.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
public class ProductRequestDTO {
    @Schema(description = "Nome do produto.",
            example = "Hamburguer")
    @NotBlank(message = "Product name cannot be blank")
    private final String name;

    @Schema(description = "Descrição do produto.",
            example = "Hamburguer de carne")
    @NotBlank(message = "Product description cannot be blank")
    private final String description;

    @Schema(description = "Preço do produto.",
            example = "25.15")
    @NotNull(message = "Product price cannot be null")
    @Positive(message = "Product price must be positive")
    private final BigDecimal price;

    @Schema(description = "Imagem do produto.")
    private final String linkImage;

    @Schema(description = "Id do Restaurante", example = "1")
    @NotNull(message = "Restaurant ID cannot be null")
    private final Long idRestaurant;
}
