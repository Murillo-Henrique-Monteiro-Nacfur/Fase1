package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUpdateDetailsUseCaseTest {
    @Mock
    private IUserGateway iUserGateway;
    @Mock
    private SessionGateway sessionGateway;
    @InjectMocks
    private UserUpdateDetailsUseCase userUpdateDetailsUseCase;

    @BeforeEach
    void setUp() {
        when(sessionGateway.getSessionDTO()).thenReturn(com.postech.fiap.fase1.core.dto.auth.SessionDTO.builder().userId(1L).userRole("ADMIN").build());
        userUpdateDetailsUseCase = new UserUpdateDetailsUseCase(iUserGateway, sessionGateway);
    }

    @Test
    void testExecute_Success() {
        UserDomain user = UserDomain.builder().build();
        user.setId(1L);
        user.setName("Novo Nome");
        user.setEmail("novo@email.com");
        when(iUserGateway.getUserById(1L)).thenReturn(user);
        when(iUserGateway.updateUser(any(UserDomain.class))).thenReturn(user);

        UserDomain result = userUpdateDetailsUseCase.execute(user);

        assertNotNull(result);
        assertEquals("Novo Nome", result.getName());
        verify(iUserGateway).getUserById(1L);
        verify(iUserGateway).updateUser(user);
    }

    @Test
    void testExecute_ValidationCalled() {
        UserDomain user = UserDomain.builder().build();
        user.setId(2L);
        when(iUserGateway.getUserById(2L)).thenReturn(user);
        when(iUserGateway.updateUser(any(UserDomain.class))).thenReturn(user);

        userUpdateDetailsUseCase.execute(user);

        verify(iUserGateway).getUserById(2L);
        verify(iUserGateway).updateUser(user);
    }

    @Test
    void testExecute_UserNotFound() {
        UserDomain user = UserDomain.builder().build();
        user.setId(99L);
        when(iUserGateway.getUserById(99L)).thenReturn(null);
        when(iUserGateway.updateUser(any(UserDomain.class))).thenThrow(new RuntimeException("User not found"));

        assertThrows(RuntimeException.class, () -> userUpdateDetailsUseCase.execute(user));
    }
}