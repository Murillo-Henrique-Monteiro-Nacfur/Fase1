package com.postech.fiap.fase1.core.presenter;

import com.postech.fiap.fase1.core.dto.address.AddressResponseDTO;
import com.postech.fiap.fase1.core.dto.address.AddressRequestDTO;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddressPresenter {

    public AddressDomain requestToAddressUserDomain(AddressRequestDTO addressRequestDTO, Long idUser) {
        return AddressDomain.builder()
                .id(addressRequestDTO.getId())
                .street(addressRequestDTO.getStreet())
                .number(addressRequestDTO.getNumber())
                .neighborhood(addressRequestDTO.getNeighborhood())
                .city(addressRequestDTO.getCity())
                .state(addressRequestDTO.getState())
                .country(addressRequestDTO.getCountry())
                .zipCode(addressRequestDTO.getZipCode())
                .addressable(UserDomain.builder().id(idUser).build())
                .build();
    }
    public AddressDomain requestToAddressRestaurantDomain(AddressRequestDTO addressRequestDTO, Long idRestaurant) {
        return AddressDomain.builder()
                .id(addressRequestDTO.getId())
                .street(addressRequestDTO.getStreet())
                .number(addressRequestDTO.getNumber())
                .neighborhood(addressRequestDTO.getNeighborhood())
                .city(addressRequestDTO.getCity())
                .state(addressRequestDTO.getState())
                .country(addressRequestDTO.getCountry())
                .zipCode(addressRequestDTO.getZipCode())
                .addressable(RestaurantDomain.builder().id(idRestaurant).build())
                .build();
    }
    public AddressDomain requestUpdateToInput(AddressRequestDTO addressRequestUpdateDTO) {
        return AddressDomain.builder()
                .id(addressRequestUpdateDTO.getId())
                .street(addressRequestUpdateDTO.getStreet())
                .number(addressRequestUpdateDTO.getNumber())
                .neighborhood(addressRequestUpdateDTO.getNeighborhood())
                .city(addressRequestUpdateDTO.getCity())
                .state(addressRequestUpdateDTO.getState())
                .country(addressRequestUpdateDTO.getCountry())
                .zipCode(addressRequestUpdateDTO.getZipCode())
                .build();
    }

    public AddressResponseDTO toDTO(AddressDomain addressDomain) {
        return AddressResponseDTO.builder()
                .id(addressDomain.getId())
                .street(addressDomain.getStreet())
                .number(addressDomain.getNumber())
                .neighborhood(addressDomain.getNeighborhood())
                .city(addressDomain.getCity())
                .state(addressDomain.getState())
                .country(addressDomain.getCountry())
                .zipCode(addressDomain.getZipCode())
                .build();
    }

    public Page<AddressResponseDTO> toDTO(Page<AddressDomain> addressDomains) {
        return addressDomains.map(this::toDTO);
    }
    public List<AddressResponseDTO> toDTO(List<AddressDomain> addressDomains) {
        return addressDomains.stream().map(this::toDTO).toList();
    }


}
