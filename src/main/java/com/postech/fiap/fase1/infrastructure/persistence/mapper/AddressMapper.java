package com.postech.fiap.fase1.infrastructure.persistence.mapper;

import com.postech.fiap.fase1.domain.model.AddressDomain;
import com.postech.fiap.fase1.infrastructure.persistence.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddressMapper {

    public Address toEntity(AddressDomain addressDomain) {
        return Address.builder()
                .id(addressDomain.getId())
                .street(addressDomain.getStreet())
                .number(addressDomain.getNumber())
                .city(addressDomain.getCity())
                .state(addressDomain.getState())
                .zipCode(addressDomain.getZipCode())
                .country(addressDomain.getCountry())
                .neighborhood(addressDomain.getNeighborhood())
                .build();
    }

    public List<Address> toEntity(List<AddressDomain> addressDomains) {
        return addressDomains.stream().map(this::toEntity).toList();
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

    public List<AddressDomain> toDomain(List<Address> addressDomains) {
        return addressDomains.stream().map(this::toDomain).toList();
    }
}
