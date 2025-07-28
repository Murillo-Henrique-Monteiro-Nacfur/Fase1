package com.postech.fiap.fase1.application.assembler;

import com.postech.fiap.fase1.application.dto.AddressDTO;
import com.postech.fiap.fase1.application.dto.AddressInputUpdateDTO;
import com.postech.fiap.fase1.application.dto.AddressRequestDTO;
import com.postech.fiap.fase1.application.dto.AddressRequestUpdateDTO;
import com.postech.fiap.fase1.infrastructure.persistence.entity.Address;
import com.postech.fiap.fase1.infrastructure.persistence.entity.User;
import com.postech.fiap.fase1.domain.model.AddressDomain;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AddressAssembler {

    public static AddressDomain requestToModel(AddressRequestDTO addressRequestDTO) {
        return AddressDomain.builder()
                .id(addressRequestDTO.getId())
                .street(addressRequestDTO.getStreet())
                .number(addressRequestDTO.getNumber())
                .neighborhood(addressRequestDTO.getNeighborhood())
                .city(addressRequestDTO.getCity())
                .state(addressRequestDTO.getState())
                .country(addressRequestDTO.getCountry())
                .zipCode(addressRequestDTO.getZipCode())
//                .addressable(UserDomain.builder().id(addressRequestDTO.getUserId())
//                        .build())
                .build();
    }

    public static AddressInputUpdateDTO requestUpdateToInput(AddressRequestUpdateDTO addressRequestUpdateDTO) {
        return AddressInputUpdateDTO.builder()
                .street(addressRequestUpdateDTO.getStreet())
                .number(addressRequestUpdateDTO.getNumber())
                .neighborhood(addressRequestUpdateDTO.getNeighborhood())
                .city(addressRequestUpdateDTO.getCity())
                .state(addressRequestUpdateDTO.getState())
                .country(addressRequestUpdateDTO.getCountry())
                .postalCode(addressRequestUpdateDTO.getPostalCode())
                .build();
    }

    public static AddressDTO toDTO(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .zipCode(address.getZipCode())
                .build();
    }

    public static Address toEntity(AddressDomain addressDomain, User user) {
        return Address.builder()
                .street(addressDomain.getStreet())
                .number(addressDomain.getNumber())
                .neighborhood(addressDomain.getNeighborhood())
                .city(addressDomain.getCity())
                .state(addressDomain.getState())
                .country(addressDomain.getCountry())
                .zipCode(addressDomain.getZipCode())
                .addressable(user)
                .build();
    }

}
