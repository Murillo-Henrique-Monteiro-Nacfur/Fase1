package com.postech.fiap.fase1.core.domain.validation.address.implementation;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.domain.model.AddressableDomain;
import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressCreateToUserAllowedValidatorTest {
    @Mock
    private ISessionGateway sessionGateway;
    @Mock
    private AddressableDomain addressable;
    @Mock
    private AddressDomain addressDomain;
    @Mock
    private SessionDTO sessionDTO;
    private AddressCreateToUserAllowedValidator validator;

    @BeforeEach
    void setUp() {
        validator = new AddressCreateToUserAllowedValidator(sessionGateway);
        when(addressDomain.getAddressable()).thenReturn(addressable);
    }

    @Test
    void validate_SessionValidationIdDoOwner() {
        Long ownerId = 10L;
        when(addressable.getIdUserOwner()).thenReturn(ownerId);
        when(sessionGateway.getSessionDTO()).thenReturn(sessionDTO);
        when(sessionDTO.isNotAdmin()).thenReturn(true);
        when(sessionDTO.getUserId()).thenReturn(ownerId);
        validator.validate(addressDomain);
        verify(sessionGateway, times(1)).getSessionDTO();
    }

    @Test
    void validate_ExceptionSessionValidation() {
        Long ownerId = 20L;
        when(addressable.getIdUserOwner()).thenReturn(ownerId);
        when(sessionGateway.getSessionDTO()).thenReturn(sessionDTO);
        when(sessionDTO.isNotAdmin()).thenReturn(true);
        when(sessionDTO.getUserId()).thenReturn(999L);
        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> validator.validate(addressDomain));
    }
}