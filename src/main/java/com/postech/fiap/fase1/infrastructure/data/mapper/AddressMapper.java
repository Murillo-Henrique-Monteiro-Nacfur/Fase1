package com.postech.fiap.fase1.infrastructure.data.mapper;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.dto.address.AddressDTO;
import com.postech.fiap.fase1.infrastructure.data.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddressMapper {

    public Address toEntity(AddressDTO addressDTO) {
        return Address.builder()
                .id(addressDTO.getId())
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .zipCode(addressDTO.getZipCode())
                .country(addressDTO.getCountry())
                .neighborhood(addressDTO.getNeighborhood())
                .build();
    }

    public List<Address> toEntity(List<AddressDTO> addressDTOs) {
        return addressDTOs.stream().map(this::toEntity).toList();
    }

    public AddressDTO toDTO(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .country(address.getCountry())
                .neighborhood(address.getNeighborhood())
                .build();
    }

    public AddressDomain toDomain(Address address) {
        return AddressDomain.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .country(address.getCountry())
                .neighborhood(address.getNeighborhood())
                .build();
    }
    public Address updateToEntity(AddressDTO addressDTO, Address address) {
        address.setStreet(addressDTO.getStreet());
        address.setNumber(addressDTO.getNumber());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZipCode(addressDTO.getZipCode());
        address.setCountry(addressDTO.getCountry());
        address.setNeighborhood(addressDTO.getNeighborhood());
        return address;
    }
    public List<AddressDTO> toDTO(List<Address> addresses) {
        return addresses.stream().map(this::toDTO).toList();
    }
}
