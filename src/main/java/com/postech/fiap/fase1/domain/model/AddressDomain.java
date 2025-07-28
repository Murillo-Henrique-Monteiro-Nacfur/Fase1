package com.postech.fiap.fase1.domain.model;

import com.postech.fiap.fase1.infrastructure.persistence.entity.Addressable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AddressDomain {
    private Long id;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private AddressableDomain addressable;

}
