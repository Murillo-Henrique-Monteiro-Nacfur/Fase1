package com.postech.fiap.fase1.domain.controller;

import com.postech.fiap.fase1.infrastructure.session.SessionService;
import com.postech.fiap.fase1.application.controller.AddressController;
import com.postech.fiap.fase1.application.dto.AddressDTO;
import com.postech.fiap.fase1.application.dto.AddressRequestDTO;
import com.postech.fiap.fase1.application.dto.AddressRequestUpdateDTO;
import com.postech.fiap.fase1.application.dto.auth.SessionDTO;
import com.postech.fiap.fase1.application.dto.auth.UserTokenDTO;
import com.postech.fiap.fase1.infrastructure.persistence.entity.Address;
import com.postech.fiap.fase1.infrastructure.persistence.entity.User;
import com.postech.fiap.fase1.domain.service.AddressService;
import com.postech.fiap.fase1.environment.EnvSessionDTO;
import com.postech.fiap.fase1.environment.EnvUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {
    @InjectMocks
    private AddressController addressController;
    @Mock
    private AddressService addressService;
    @Mock
    private SessionService sessionService;

    @DisplayName("Should create a new address and return AddressDTO")
    @Test
    void createNewAddressAndReturnAddressDTO() {
        AddressRequestDTO requestDTO = new AddressRequestDTO("Street B", "456", "Neighborhood B", "City B", "State B", "Country B", "67890", 1L);
        SessionDTO sessionDTO = new SessionDTO(UserTokenDTO.builder().id(1L).build());
        AddressDTO expectedAddressDTO = new AddressDTO(2L, "Street B", "456", "Neighborhood B", "City B", "State B", "Country B", "67890", 1L);

        when(sessionService.getSessionDTO()).thenReturn(sessionDTO);
        when(addressService.createAddress(any(), eq(sessionDTO))).thenReturn(expectedAddressDTO);

        ResponseEntity<AddressDTO> response = addressController.create(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedAddressDTO, response.getBody());
    }

    @DisplayName("Should update an existing address and return AddressDTO")
    @Test
    void updateExistingAddressAndReturnAddressDTO() {
        Long addressId = 1L;
        AddressRequestUpdateDTO updateDTO = new AddressRequestUpdateDTO("Street C", "789", "Neighborhood C", "City C", "State C", "Country C", "54321");
        Address address = new Address(1L, "Street C", "789", "Neighborhood C", "City C", "State C", "Country C", "54321", User.builder().id(1L).build());
        when(addressService.updateAddress(eq(addressId), any())).thenReturn(address);

        ResponseEntity<AddressDTO> response = addressController.update(addressId, updateDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @DisplayName("Should delete an address and return 200 status")
    @Test
    void deleteAddressAndReturn200Status() {
        Long addressId = 1L;
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTOClient();
        User user = EnvUser.getUserClient();
        when(sessionService.getSessionDTO()).thenReturn(sessionDTO);

        doNothing().when(addressService).delete(addressId, sessionDTO);

        ResponseEntity<Void> response = addressController.delete(addressId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(addressService, times(1)).delete(addressId, sessionDTO);
    }
}