package com.postech.fiap.fase1.core.domain.usecase.address;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
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
class AddressUpdateUseCaseTest {

    @Mock
    private IAddressGateway iAddressGateway;
    @Mock
    private ISessionGateway iSessionGateway;
    @InjectMocks
    private AddressUpdateUseCase addressUpdateUseCase;

    @BeforeEach
    void setUp() {
        addressUpdateUseCase = new AddressUpdateUseCase(iAddressGateway, iSessionGateway);
    }

    @Test
    void execute_UpdateAdress_UserAuthoridzed() {
        AddressDomain addressDomain = AddressDomain.builder().id(1L).build();
        UserDomain userDomain = UserDomain.builder().id(10L).build();
        addressDomain.setAddressable(userDomain);

        AddressDomain addressDomainOld = AddressDomain.builder().id(1L).build();
        addressDomainOld.setAddressable(userDomain);

        when(iAddressGateway.getOneByid(1L)).thenReturn(addressDomainOld);
        when(iSessionGateway.getSessionDTO()).thenReturn(SessionDTO.builder().userId(10L).userLogin("testuser").build()); // Supondo que a validação passa
        when(iAddressGateway.update(addressDomain)).thenReturn(addressDomain);

        AddressDomain result = addressUpdateUseCase.execute(addressDomain);

        assertNotNull(result);
        assertEquals(addressDomain, result);
        verify(iAddressGateway, times(1)).update(addressDomain);
    }

    @Test
    void execute_Exception_UserUnauthorized() {
        AddressDomain addressDomain = AddressDomain.builder().id(1L).build();
        UserDomain userDomain = UserDomain.builder().id(10L).build();
        addressDomain.setAddressable(userDomain);

        AddressDomain addressDomainOld = AddressDomain.builder().id(1L).build();
        addressDomainOld.setAddressable(userDomain);

        when(iAddressGateway.getOneByid(1L)).thenReturn(addressDomainOld);
        doThrow(new ApplicationException("User not authorized to perform this action")).when(iSessionGateway).getSessionDTO();

        assertThrows(ApplicationException.class, () -> addressUpdateUseCase.execute(addressDomain));
        verify(iAddressGateway, never()).update(addressDomain);
    }
}