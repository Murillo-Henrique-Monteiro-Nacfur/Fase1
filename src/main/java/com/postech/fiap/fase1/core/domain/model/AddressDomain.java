package com.postech.fiap.fase1.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@AllArgsConstructor

public class AddressDomain {
    private static final String USER = "User";
    private static final String RESTAURANT = "Restaurant";
    @Setter
    private Long id;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    @Setter
    private AddressableDomain addressable;

    @Setter
    private String addressableType;


    public boolean isUserAddress(){
        return USER.equals(addressableType);
    }

    public boolean isRestaurantAddress(){
        return RESTAURANT.equals(addressableType);
    }
}
