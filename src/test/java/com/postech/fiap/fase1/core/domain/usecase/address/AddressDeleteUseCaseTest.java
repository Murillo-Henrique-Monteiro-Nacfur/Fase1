package com.postech.fiap.fase1.core.domain.usecase.address;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.domain.model.AddressableDomain;
import com.postech.fiap.fase1.core.domain.validation.session.ISessionValidation;
import com.postech.fiap.fase1.core.gateway.address.IAddressGateway;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressDeleteUseCaseTest {
    @Mock
    private IAddressGateway addressGateway;
    @Mock
    private ISessionGateway sessionGateway;
    @Mock
    private ISessionValidation sessionValidation;
    @Mock
    private AddressDomain addressDomain;
    @Mock
    private AddressableDomain addressableDomain;
    @InjectMocks
    private AddressDeleteUseCase addressDeleteUseCase;

    @BeforeEach
    void setUp() throws Exception {
        addressDeleteUseCase = new AddressDeleteUseCase(addressGateway, sessionGateway);
        var field = AddressDeleteUseCase.class.getDeclaredField("sessionValidation");
        field.setAccessible(true);
        field.set(addressDeleteUseCase, sessionValidation);
    }

    @Test
    void execute_shouldDeleteAddress_whenUserIsAuthorized() {
        Long addressId = 1L;
        Long userOwnerId = 10L;
        when(addressGateway.getOneByid(addressId)).thenReturn(addressDomain);
        when(addressDomain.getAddressable()).thenReturn(addressableDomain);
        when(addressableDomain.getIdUserOwner()).thenReturn(userOwnerId);
        doNothing().when(sessionValidation).validate(userOwnerId);
        doNothing().when(addressGateway).deleteById(addressId);

        assertDoesNotThrow(() -> addressDeleteUseCase.execute(addressId));
        verify(addressGateway).deleteById(addressId);
    }

    @Test
    void execute_shouldThrowException_whenUserIsUnauthorized() {
        Long addressId = 1L;
        Long userOwnerId = 10L;
        when(addressGateway.getOneByid(addressId)).thenReturn(addressDomain);
        when(addressDomain.getAddressable()).thenReturn(addressableDomain);
        when(addressableDomain.getIdUserOwner()).thenReturn(userOwnerId);
        doThrow(new ApplicationException("Unauthorized")).when(sessionValidation).validate(userOwnerId);

        ApplicationException ex = assertThrows(ApplicationException.class, () -> addressDeleteUseCase.execute(addressId));
        assertEquals("Unauthorized", ex.getMessage());
        verify(addressGateway, never()).deleteById(addressId);
    }

    @Test
    void execute_shouldThrowException_whenAddressNotFound() {
        Long addressId = 1L;
        when(addressGateway.getOneByid(addressId)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> addressDeleteUseCase.execute(addressId));
        verify(addressGateway, never()).deleteById(addressId);
    }
}