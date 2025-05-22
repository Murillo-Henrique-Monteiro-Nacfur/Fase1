package com.postech.fiap.fase1.domain.assembler;

import com.postech.fiap.fase1.domain.dto.AdressDTO;
import com.postech.fiap.fase1.domain.dto.AdressInputDTO;
import com.postech.fiap.fase1.domain.dto.AdressRequestDTO;
import com.postech.fiap.fase1.domain.model.Adress;
import com.postech.fiap.fase1.domain.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AdressAssembler {

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

    public static AdressDTO toDTO(Adress adress) {
        return AdressDTO.builder()
                .id(adress.getId())
                .street(adress.getStreet())
                .number(adress.getNumber())
                .neighborhood(adress.getNeighborhood())
                .city(adress.getCity())
                .state(adress.getState())
                .country(adress.getCountry())
                .zipCode(adress.getZipCode())
                .userId(adress.getUser().getId())
                .build();
    }

    public static Adress toEntity(AdressInputDTO adressInputDTO, User user) {
        return Adress.builder()
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
