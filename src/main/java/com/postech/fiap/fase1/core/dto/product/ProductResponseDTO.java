package com.postech.fiap.fase1.core.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String photoUrl;

}
