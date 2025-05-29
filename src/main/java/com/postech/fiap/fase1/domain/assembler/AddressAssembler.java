package com.postech.fiap.fase1.domain.assembler;

import com.postech.fiap.fase1.domain.dto.AddressDTO;
import com.postech.fiap.fase1.domain.dto.AdressInputDTO;
import com.postech.fiap.fase1.domain.dto.AdressRequestDTO;
import com.postech.fiap.fase1.domain.model.Address;
import com.postech.fiap.fase1.domain.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AddressAssembler {

    public static AdressInputDTO requestToInput(AdressRequestDTO adressRequestDTO) {
        return AdressInputDTO.builder()
                .street(adressRequestDTO.getStreet())
                .number(adressRequestDTO.getNumber())
                .complement(adressRequestDTO.getComplement())
                .neighborhood(adressRequestDTO.getNeighborhood())
                .city(adressRequestDTO.getCity())
                .state(adressRequestDTO.getState())
                .country(adressRequestDTO.getCountry())
                .postalCode(adressRequestDTO.getPostalCode())
                .userId(adressRequestDTO.getUserId())
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

    public static Address toEntity(AdressInputDTO adressInputDTO, User user) {
        return Address.builder()
                .street(adressInputDTO.getStreet())
                .number(adressInputDTO.getNumber())
                .neighborhood(adressInputDTO.getNeighborhood())
                .city(adressInputDTO.getCity())
                .state(adressInputDTO.getState())
                .country(adressInputDTO.getCountry())
                .zipCode(adressInputDTO.getPostalCode())
                .user(user)
                .build();
    }

}
