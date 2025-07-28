package com.postech.fiap.fase1.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class RestaurantDomain implements AddressableDomain {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private String cnpj;
    private LocalTime openTime;
    private LocalTime closeTime;
    private UserDomain user;
    private List<AddressDomain> addresses;


    public boolean isOpenAndCloseTimeInvalids() {
        return openTime.isAfter(closeTime);
    }
}
