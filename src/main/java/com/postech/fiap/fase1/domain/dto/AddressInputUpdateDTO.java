package com.postech.fiap.fase1.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AddressInputUpdateDTO {
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private String postalCode;
}
