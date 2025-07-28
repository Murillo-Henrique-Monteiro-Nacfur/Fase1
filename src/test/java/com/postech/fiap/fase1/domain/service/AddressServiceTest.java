package com.postech.fiap.fase1.domain.service;

import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.application.dto.AddressDTO;
import com.postech.fiap.fase1.domain.model.AddressDomain;
import com.postech.fiap.fase1.application.dto.AddressInputUpdateDTO;
import com.postech.fiap.fase1.application.dto.auth.SessionDTO;
import com.postech.fiap.fase1.infrastructure.persistence.entity.Address;
import com.postech.fiap.fase1.infrastructure.persistence.entity.User;
import com.postech.fiap.fase1.infrastructure.persistence.repository.AddressRepository;
import com.postech.fiap.fase1.domain.validator.UserValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    @InjectMocks
    private AddressService addressService;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private UserService userService;
    @Mock
    private UserValidator userValidator;

    @DisplayName("Should create address when user does not already have one")
    @Test
    void createAddressWhenUserDoesNotAlreadyHaveOne() {
        AddressDomain addressDomain = new AddressDomain("Street A", "123", "Neighborhood A", "City A", "State A", "Country A", "12345", 1L);
        SessionDTO sessionDTO = SessionDTO.builder().userId(1L).build();
        User user = User.builder().id(1L).build();
        Address address = new Address(1L, "Street A", "123", "City A", "State A", "12345", "Country A", "Neighborhood A", user);

        when(userService.getOne(sessionDTO, addressDomain.getUserId())).thenReturn(user);
        doNothing().when(userValidator).verifyUserLoggedIsAdminOrOwner(sessionDTO, user.getId());
        when(addressRepository.existsByUserId(user.getId())).thenReturn(false);
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        AddressDTO result = addressService.createAddress(addressDomain, sessionDTO);

        assertEquals("Street A", result.getStreet());
        assertEquals("123", result.getNumber());
        assertEquals("City A", result.getCity());
        assertEquals("State A", result.getState());
        assertEquals("Country A", result.getCountry());
        assertEquals("12345", result.getZipCode());
    }

    @DisplayName("Should throw exception when user already has an address")
    @Test
    void throwExceptionWhenUserAlreadyHasAnAddress() {
        AddressDomain addressDomain = new AddressDomain("Street A", "123", "Neighborhood A", "City A", "State A", "Country A", "12345", 1L);
        SessionDTO sessionDTO = SessionDTO.builder().userId(1L).build();
        User user = User.builder().id(1L).build();

        when(userService.getOne(sessionDTO, addressDomain.getUserId())).thenReturn(user);
        doNothing().when(userValidator).verifyUserLoggedIsAdminOrOwner(sessionDTO, user.getId());
        when(addressRepository.existsByUserId(user.getId())).thenReturn(true);

        ApplicationException exception = assertThrows(ApplicationException.class, () -> addressService.createAddress(addressDomain, sessionDTO));

        assertEquals("User already has an address", exception.getMessage());
    }

    @DisplayName("Should return empty list when no addresses are found")
    @Test
    void returnEmptyListWhenNoAddressesAreFound() {
        when(addressRepository.findAll()).thenReturn(List.of());

        List<AddressDTO> addresses = addressService.findAll();

        assertTrue(addresses.isEmpty());
    }

    @DisplayName("Should return all addresses when addresses exist")
    @Test
    void returnAllAddressesWhenAddressesExist() {
        List<Address> addresses = List.of(
                new Address(1L, "Street A", "123", "Neighborhood A", "City A", "State A", "Country A", "12345", User.builder().id(1L).build()),
                new Address(2L, "Street B", "456", "Neighborhood B", "City B", "State B", "Country B", "67890", User.builder().id(2L).build())
        );

        when(addressRepository.findAll()).thenReturn(addresses);

        List<AddressDTO> result = addressService.findAll();

        assertEquals(2, result.size());
        assertEquals("Street A", result.get(0).getStreet());
        assertEquals("Street B", result.get(1).getStreet());
    }

    @DisplayName("Should return empty list when no addresses exist")
    @Test
    void returnEmptyListWhenNoAddressesExist() {
        when(addressRepository.findAll()).thenReturn(List.of());

        List<AddressDTO> result = addressService.findAll();

        assertTrue(result.isEmpty());
    }

    @DisplayName("Should throw exception when updating non-existent address")
    @Test
    void throwExceptionWhenUpdatingNonExistentAddress() {
        Long addressId = 99L;
        AddressInputUpdateDTO updateDTO = new AddressInputUpdateDTO("Street C", "789", "Neighborhood C", "City C", "State C", "Country C", "54321");

        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> addressService.updateAddress(addressId, updateDTO));

        assertEquals("Address not found", exception.getMessage());
    }

    @DisplayName("Should update address when it exists")
    @Test
    void updateAddressWhenItExists() {
        Long addressId = 1L;
        AddressInputUpdateDTO updateDTO = new AddressInputUpdateDTO("Street C", "789", "Neighborhood C", "City C", "State C", "Country C", "54321");
        Address existingAddress = new Address(1L, "Street A", "123","City A",   "State A","12345", "Country A", "Neighborhood A", User.builder().id(1L).build());
        Address updatedAddress = new Address(1L, "Street C", "789", "City C",  "State C","54321", "Country C", "Neighborhood C", existingAddress.getUser());

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(existingAddress));
        when(addressRepository.save(existingAddress)).thenReturn(updatedAddress);

        Address result = addressService.updateAddress(addressId, updateDTO);

        assertEquals("Street C", result.getStreet());
        assertEquals("789", result.getNumber());
        assertEquals("Neighborhood C", result.getNeighborhood());
        assertEquals("City C", result.getCity());
        assertEquals("State C", result.getState());
        assertEquals("Country C", result.getCountry());
        assertEquals("54321", result.getZipCode());
    }

    @DisplayName("Should throw exception when deleting address without permission")
    @Test
    void throwExceptionWhenDeletingAddressWithoutPermission() {
        Long addressId = 1L;
        SessionDTO sessionDTO = SessionDTO.builder().build();
        Address address = new Address(1L, "Street A", "123", "Neighborhood A", "City A", "State A", "Country A", "12345", User.builder().id(2L).build());

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        doThrow(new ApplicationException("User does not have permission")).when(userValidator).verifyUserLoggedIsAdminOrOwner(sessionDTO, address.getUser().getId());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> addressService.delete(addressId, sessionDTO));

        assertEquals("User does not have permission", exception.getMessage());
        verify(addressRepository, never()).delete(any(Address.class));
    }

    @DisplayName("Should delete address when user has permission")
    @Test
    void deleteAddressWhenUserHasPermission() {
        Long addressId = 1L;
        SessionDTO sessionDTO = SessionDTO.builder().userId(1L).build();
        Address address = new Address(1L, "Street A", "123", "Neighborhood A", "City A", "State A", "Country A", "12345", User.builder().id(1L).build());

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        doNothing().when(userValidator).verifyUserLoggedIsAdminOrOwner(sessionDTO, address.getUser().getId());
        doNothing().when(addressRepository).delete(address);

        addressService.delete(addressId, sessionDTO);

        verify(addressRepository).delete(address);
    }

    @DisplayName("Should return address when user has permission")
    @Test
    void returnAddressWhenUserHasPermission() {
        Long addressId = 1L;
        SessionDTO sessionDTO = SessionDTO.builder().userId(1L).build();
        Address address = new Address(1L, "Street A", "123", "Neighborhood A", "City A", "State A", "Country A", "12345", User.builder().id(1L).build());

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        doNothing().when(userValidator).verifyUserLoggedIsAdminOrOwner(sessionDTO, address.getUser().getId());

        Address result = addressService.getOneById(addressId, sessionDTO);

        assertEquals(address, result);
    }
}