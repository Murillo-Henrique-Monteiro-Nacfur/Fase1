package com.postech.fiap.fase1.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

import static java.util.Objects.isNull;

@Builder
@Getter
@AllArgsConstructor
public class RestaurantDomain implements AddressableDomain {
    @Setter
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private String cnpj;
    private LocalTime openTime;
    private LocalTime closeTime;
    @Setter
    private UserDomain user;
    private List<AddressDomain> addresses;


    public boolean isOpenAndCloseTimeInvalids() {
        return openTime.isAfter(closeTime);
    }

    @Override
    public Long getIdUserOwner() {
        return isNull(user) ? null : user.getId();
    }
}
