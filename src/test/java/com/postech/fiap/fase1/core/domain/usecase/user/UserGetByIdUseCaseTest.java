package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserGetByIdUseCaseTest {
    @Mock
    private IUserGateway iUserGateway;
    @InjectMocks
    private UserGetByIdUseCase userGetByIdUseCase;

    @BeforeEach
    void setUp() {
        userGetByIdUseCase = new UserGetByIdUseCase(iUserGateway);
    }

    @Test
    void testGetUserByLogin_Success() {
        UserDomain user = UserDomain.builder().build();
        user.setId(1L);
        user.setLogin("testuser");
        when(iUserGateway.getUserByLogin("testuser")).thenReturn(Optional.of(user));

        UserDomain result = userGetByIdUseCase.getUserByLogin("testuser");
        assertNotNull(result);
        assertEquals("testuser", result.getLogin());
        verify(iUserGateway).getUserByLogin("testuser");
    }

    @Test
    void testGetUserByLogin_UserNotFound() {
        when(iUserGateway.getUserByLogin("notfound")).thenReturn(Optional.empty());
        ApplicationException exception = assertThrows(ApplicationException.class, () -> userGetByIdUseCase.getUserByLogin("notfound"));
        assertEquals("User not found", exception.getMessage());
        verify(iUserGateway).getUserByLogin("notfound");
    }
}