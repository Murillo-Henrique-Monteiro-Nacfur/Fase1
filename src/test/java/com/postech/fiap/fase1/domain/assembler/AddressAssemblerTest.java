package com.postech.fiap.fase1.domain.assembler;

import com.postech.fiap.fase1.domain.dto.*;
import com.postech.fiap.fase1.domain.model.Address;
import com.postech.fiap.fase1.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AddressAssemblerTest {
    @DisplayName("Should map AddressRequestDTO to AddressInputDTO correctly")
    @Test
    void mapAddressRequestDTOToAddressInputDTO() {
        AddressRequestDTO requestDTO = new AddressRequestDTO("Street A", "123", "Neighborhood A", "City A", "State A", "Country A", "12345", 1L);

        AddressInputDTO inputDTO = AddressAssembler.requestToInput(requestDTO);

        assertEquals("Street A", inputDTO.getStreet());
        assertEquals("123", inputDTO.getNumber());
        assertEquals("Neighborhood A", inputDTO.getNeighborhood());
        assertEquals("City A", inputDTO.getCity());
        assertEquals("State A", inputDTO.getState());
        assertEquals("Country A", inputDTO.getCountry());
        assertEquals("12345", inputDTO.getPostalCode());
        assertEquals(1L, inputDTO.getUserId());
    }

    @DisplayName("Should map AddressRequestUpdateDTO to AddressInputUpdateDTO correctly")
    @Test
    void mapAddressRequestUpdateDTOToAddressInputUpdateDTO() {
        AddressRequestUpdateDTO requestUpdateDTO = new AddressRequestUpdateDTO("Street B", "456", "Neighborhood B", "City B", "State B", "Country B", "67890");

        AddressInputUpdateDTO inputUpdateDTO = AddressAssembler.requestUpdateToInput(requestUpdateDTO);

        assertEquals("Street B", inputUpdateDTO.getStreet());
        assertEquals("456", inputUpdateDTO.getNumber());
        assertEquals("Neighborhood B", inputUpdateDTO.getNeighborhood());
        assertEquals("City B", inputUpdateDTO.getCity());
        assertEquals("State B", inputUpdateDTO.getState());
        assertEquals("Country B", inputUpdateDTO.getCountry());
        assertEquals("67890", inputUpdateDTO.getPostalCode());
    }

    @DisplayName("Should map Address to AddressDTO correctly")
    @Test
    void mapAddressToAddressDTO() {
        User user = User.builder().id(1L).build();
        Address address = new Address(1L, "Street C", "789", "City C", "State C", "54321", "Country C", "Neighborhood C", user);

        AddressDTO addressDTO = AddressAssembler.toDTO(address);

        assertEquals(1L, addressDTO.getId());
        assertEquals("Street C", addressDTO.getStreet());
        assertEquals("789", addressDTO.getNumber());
        assertEquals("Neighborhood C", addressDTO.getNeighborhood());
        assertEquals("City C", addressDTO.getCity());
        assertEquals("State C", addressDTO.getState());
        assertEquals("Country C", addressDTO.getCountry());
        assertEquals("54321", addressDTO.getZipCode());
        assertEquals(1L, addressDTO.getUserId());
    }

    @DisplayName("Should map AddressInputDTO and User to Address correctly")
    @Test
    void mapAddressInputDTOAndUserToAddress() {
        AddressInputDTO inputDTO = new AddressInputDTO("Street D", "101", "Neighborhood D", "City D", "State D", "Country D", "98765", 2L);
        User user = User.builder().id(2L).build();

        Address address = AddressAssembler.toEntity(inputDTO, user);

        assertEquals("Street D", address.getStreet());
        assertEquals("101", address.getNumber());
        assertEquals("Neighborhood D", address.getNeighborhood());
        assertEquals("City D", address.getCity());
        assertEquals("State D", address.getState());
        assertEquals("Country D", address.getCountry());
        assertEquals("98765", address.getZipCode());
        assertEquals(user, address.getUser());
    }
}