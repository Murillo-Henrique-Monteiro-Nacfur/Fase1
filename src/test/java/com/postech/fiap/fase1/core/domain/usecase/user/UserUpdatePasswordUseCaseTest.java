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
class UserUpdatePasswordUseCaseTest {

    @Mock
    private IUserGateway iUserGateway;
    @Mock
    private SessionGateway sessionGateway;
    @InjectMocks
    private UserUpdatePasswordUseCase userUpdatePasswordUseCase;

    @BeforeEach
    void setUp() {
        when(sessionGateway.getSessionDTO()).thenReturn(com.postech.fiap.fase1.core.dto.auth.SessionDTO.builder().userId(1L).userRole("ADMIN").build());
        userUpdatePasswordUseCase = new UserUpdatePasswordUseCase(iUserGateway, sessionGateway);
    }

    @Test
    void testExecute_ShouldUpdatePassword_WhenValid() {
        UserDomain user = UserDomain.builder().id(1L).password("plainPassword").passwordConfirmation("plainPassword").build();
        when(iUserGateway.getUserById(1L)).thenReturn(user);
        when(iUserGateway.updateUserPassoword(any(UserDomain.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDomain result = userUpdatePasswordUseCase.execute(user);

        assertNotNull(result);
        assertNotEquals("plainPassword", result.getPassword());
        assertTrue(result.getPassword().startsWith("$2a$")); // BCrypt hash
        verify(iUserGateway, times(1)).getUserById(1L);
        verify(iUserGateway, times(1)).updateUserPassoword(any(UserDomain.class));
    }

    @Test
    void testExecute_ShouldThrowException_WhenUserNotFound() {
        UserDomain user = UserDomain.builder().id(2L).password("otherPassword").build();
        when(iUserGateway.getUserById(2L)).thenReturn(null);


        assertThrows(RuntimeException.class, () -> userUpdatePasswordUseCase.execute(user));
        verify(iUserGateway, times(1)).getUserById(2L);
    }
}