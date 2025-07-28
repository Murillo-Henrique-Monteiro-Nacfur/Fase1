package com.postech.fiap.fase1.application.disassembler;

import com.postech.fiap.fase1.application.dto.AddressDTO;
import com.postech.fiap.fase1.domain.model.AddressDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddressDisassembler {

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
    public List<AddressDTO> toDTO(List<AddressDomain> addressDomains) {
        return addressDomains.stream().map(this::toDTO).toList();
    }
}
