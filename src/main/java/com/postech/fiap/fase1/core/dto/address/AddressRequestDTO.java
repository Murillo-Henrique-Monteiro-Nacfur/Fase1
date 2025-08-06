package com.postech.fiap.fase1.core.dto.address;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AddressRequestDTO {
    @Schema(description = "Identificador único do endereço.",
            example = "101")
    private Long id;
    @Schema(description = "Nome da rua do endereço.",
            example = "Rua das Flores")
    @NotBlank
    private String street;
    @Schema(description = "Número do endereço.",
            example = "123")
    @NotBlank
    private String number;
    @Schema(description = "Bairro do endereço.",
            example = "Centro")
    @NotBlank
    private String neighborhood;
    @Schema(description = "Cidade do endereço.",
            example = "São Paulo")
    @NotBlank
    private String city;
    @Schema(description = "Estado do endereço.",
            example = "SP")
    @NotBlank
    private String state;
    @Schema(description = "País do endereço.",
            example = "Brasil")
    @NotBlank
    private String country;
    @Schema(description = "CEP do endereço.",
            example = "16999999")
    @NotBlank
    private String zipCode;

}
