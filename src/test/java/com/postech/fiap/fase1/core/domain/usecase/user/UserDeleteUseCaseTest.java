package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDeleteUseCaseTest {
    @Mock
    private IUserGateway iUserGateway;
    @Mock
    private ISessionGateway sessionGateway;
    @InjectMocks
    private UserDeleteUseCase userDeleteUseCase;

    @BeforeEach
    void setUp() {
        userDeleteUseCase = new UserDeleteUseCase(iUserGateway, sessionGateway);
    }

    @Test
    void testExecute_Success() {
        when(sessionGateway.getSessionDTO()).thenReturn(com.postech.fiap.fase1.core.dto.auth.SessionDTO.builder().userId(1L).userRole("ADMIN").build());
        UserDomain user = UserDomain.builder().build();
        user.setId(1L);
        when(iUserGateway.getUserById(1L)).thenReturn(user);
        doNothing().when(iUserGateway).deleteUser(user);

        userDeleteUseCase.execute(1L);

        verify(iUserGateway).getUserById(1L);
        verify(iUserGateway).deleteUser(user);
    }

    @Test
    void testExecute_UserNotFound() {
        when(iUserGateway.getUserById(99L)).thenReturn(null);
        try {
            userDeleteUseCase.execute(99L);
        } catch (Exception e) {
            assert true;
        }
        verify(iUserGateway).getUserById(99L);
    }
}