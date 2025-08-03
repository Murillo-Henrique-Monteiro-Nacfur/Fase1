package com.postech.fiap.fase1.core.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDomain {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean available;
    private String photoUrl;
    private RestaurantDomain restaurant;
}
