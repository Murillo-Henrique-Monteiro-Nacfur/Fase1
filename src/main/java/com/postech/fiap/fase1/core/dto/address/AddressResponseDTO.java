package com.postech.fiap.fase1.core.dto.address;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDTO {
    @Schema(description = "Identificador único do endereço.",
            example = "101")
    private Long id;
    @Schema(description = "Nome da rua do endereço.",
            example = "Rua das Flores")
    private String street;
    @Schema(description = "Número do endereço.",
            example = "123")
    private String number;
    @Schema(description = "Bairro do endereço.",
            example = "Centro")
    private String neighborhood;
    @Schema(description = "Cidade do endereço.",
            example = "São Paulo")
    private String city;
    @Schema(description = "Estado do endereço.",
            example = "SP")
    private String state;
    @Schema(description = "País do endereço.",
            example = "Brasil")
    private String country;
    @Schema(description = "CEP do endereço.",
            example = "16999999")
    private String zipCode;
}
