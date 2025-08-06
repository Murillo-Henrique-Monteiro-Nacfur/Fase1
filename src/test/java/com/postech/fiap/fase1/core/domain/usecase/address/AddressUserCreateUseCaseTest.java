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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressUserCreateUseCaseTest {

    @Mock
    private IAddressGateway iAddressGateway;

    @Mock
    private ISessionGateway iSessionGateway;

    @InjectMocks
    private AddressUserCreateUseCase addressUserCreateUseCase;

    @BeforeEach
    void setUp() {
    }

    @Test
    void execute_AdressCreate_UserAuthorized() {
        AddressDomain addressDomain = AddressDomain.builder().build();
        UserDomain userDomain = UserDomain.builder().id(1L).build();
        userDomain.setLogin("testuser");
        addressDomain.setAddressable(userDomain);

        SessionDTO sessionDTO = SessionDTO.builder().userId(1L).userLogin("testuser").build();

        when(iSessionGateway.getSessionDTO()).thenReturn(sessionDTO);
        when(iAddressGateway.createUserAddress(addressDomain)).thenReturn(addressDomain);

        AddressDomain result = addressUserCreateUseCase.execute(addressDomain);

        assertNotNull(result);
        verify(iAddressGateway, times(1)).createUserAddress(addressDomain);
    }

    @Test
    void execute_Exception_UserUnauthorized() {
        AddressDomain addressDomain = AddressDomain.builder().build();
        UserDomain userDomain = UserDomain.builder().id(2L).build();
        userDomain.setLogin("testuser");
        addressDomain.setAddressable(userDomain);

        SessionDTO sessionDTO = SessionDTO.builder().userId(1L).userLogin("anotheruser").build();

        when(iSessionGateway.getSessionDTO()).thenReturn(sessionDTO);

        assertThrows(ApplicationException.class, () -> addressUserCreateUseCase.execute(addressDomain));

        verify(iAddressGateway, never()).createUserAddress(addressDomain);
    }
}