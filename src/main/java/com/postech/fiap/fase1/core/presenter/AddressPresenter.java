package com.postech.fiap.fase1.core.presenter;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.domain.model.AddressableDomain;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.dto.address.AddressDTO;
import com.postech.fiap.fase1.core.dto.address.AddressRequestDTO;
import com.postech.fiap.fase1.core.dto.address.AddressResponseDTO;
import com.postech.fiap.fase1.core.dto.address.AddressableDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import org.springframework.data.domain.Page;

import java.util.List;

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

    public AddressDTO toAddressDTO(AddressDomain addressDomain, AddressableDTO addressableDTO) {
        return AddressDTO.builder()
                .id(addressDomain.getId())
                .street(addressDomain.getStreet())
                .number(addressDomain.getNumber())
                .neighborhood(addressDomain.getNeighborhood())
                .city(addressDomain.getCity())
                .state(addressDomain.getState())
                .country(addressDomain.getCountry())
                .zipCode(addressDomain.getZipCode())
                .addressable(addressableDTO)
                .build();
    }

    public AddressDTO toDTO(AddressDomain addressDomain) {
        return AddressDTO.builder()
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

    public AddressDomain toDomain(AddressDTO addressDTO) {
        AddressableDomain addressableDomain = getAddressableDomain(addressDTO.getAddressable());
        return AddressDomain.builder()
                .id(addressDTO.getId())
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .neighborhood(addressDTO.getNeighborhood())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .country(addressDTO.getCountry())
                .zipCode(addressDTO.getZipCode())
                .addressable(addressableDomain)
                .build();
    }

    public List<AddressDomain> toDomain(List<AddressDTO> addressDTOS) {
        return addressDTOS.stream().map(this::toDomain).toList();
    }

    private AddressableDomain getAddressableDomain(AddressableDTO addressable) {
        var userPresenter = new UserPresenter();
        var restaurantPresenter = new RestaurantPresenter();
        if (addressable instanceof UserDTO userDTO) {
            return userPresenter.toDomain(userDTO);
        }
        if (addressable instanceof RestaurantDTO restaurantDTO) {
            return restaurantPresenter.toDomain(restaurantDTO);
        }
        return null;
    }

    public AddressResponseDTO toResponseDTO(AddressDomain addressDomain) {
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

    public Page<AddressResponseDTO> toResponseDTO(Page<AddressDomain> addressDomains) {
        return addressDomains.map(this::toResponseDTO);
    }

    public List<AddressResponseDTO> toResponseDTO(List<AddressDomain> addressDomains) {
        return addressDomains.stream().map(this::toResponseDTO).toList();
    }


}
