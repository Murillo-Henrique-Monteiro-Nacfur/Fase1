package com.postech.fiap.fase1.domain.assembler;

import com.postech.fiap.fase1.domain.dto.*;
import com.postech.fiap.fase1.domain.model.Address;
import com.postech.fiap.fase1.domain.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AddressAssembler {

    public static AddressInputDTO requestToInput(AdressRequestDTO adressRequestDTO) {
        return AddressInputDTO.builder()
                .street(adressRequestDTO.getStreet())
                .number(adressRequestDTO.getNumber())
                .neighborhood(adressRequestDTO.getNeighborhood())
                .city(adressRequestDTO.getCity())
                .state(adressRequestDTO.getState())
                .country(adressRequestDTO.getCountry())
                .postalCode(adressRequestDTO.getPostalCode())
                .userId(adressRequestDTO.getUserId())
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
                .userId(address.getUser().getId())
                .build();
    }

    public static Address toEntity(AddressInputDTO addressInputDTO, User user) {
        return Address.builder()
                .street(addressInputDTO.getStreet())
                .number(addressInputDTO.getNumber())
                .neighborhood(addressInputDTO.getNeighborhood())
                .city(addressInputDTO.getCity())
                .state(addressInputDTO.getState())
                .country(addressInputDTO.getCountry())
                .zipCode(addressInputDTO.getPostalCode())
                .user(user)
                .build();
    }

}
