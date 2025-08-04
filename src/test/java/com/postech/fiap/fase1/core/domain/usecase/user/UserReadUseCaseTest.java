package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserReadUseCaseTest {

    @Mock
    private IUserGateway iUserGateway;
    @Mock
    private SessionGateway sessionGateway;
    @InjectMocks
    private UserReadUseCase userReadUseCase;

    @BeforeEach
    void setUp() {
        when(sessionGateway.getSessionDTO()).thenReturn(SessionDTO.builder().userId(1L).userRole("ADMIN").build());
        userReadUseCase = new UserReadUseCase(iUserGateway, sessionGateway);
    }

    @Test
    void testGetById() {
        Long userId = 1L;
        UserDomain user = UserDomain.builder().id(userId).build();
        when(iUserGateway.getUserById(userId)).thenReturn(user);

        UserDomain result = userReadUseCase.getById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(iUserGateway, times(1)).getUserById(userId);
    }

    @Test
    void testGetAllPaged() {
        Pageable pageable = mock(Pageable.class);
        UserDomain user = UserDomain.builder().id(1L).build();
        Page<UserDomain> page = new PageImpl<>(Collections.singletonList(user));
        when(iUserGateway.getAllUserPaged(pageable)).thenReturn(page);

        Page<UserDomain> result = userReadUseCase.getAllPaged(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(iUserGateway, times(1)).getAllUserPaged(pageable);
    }
}