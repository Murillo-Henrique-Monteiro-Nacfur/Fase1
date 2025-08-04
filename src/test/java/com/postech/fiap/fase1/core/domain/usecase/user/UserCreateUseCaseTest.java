package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserCreateUseCaseTest {
    private IUserGateway iUserGateway;
    private UserCreateUseCase userCreateUseCase;

    @BeforeEach
    void setUp() {
        iUserGateway = Mockito.mock(IUserGateway.class);
        userCreateUseCase = new UserCreateUseCase(iUserGateway);
    }

    private UserDomain createUserDomain() {
        UserDomain user = UserDomain.builder().build();
        user.setName("Test User");
        user.setLogin("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setPasswordConfirmation("password123");
        return user;
    }

    @Test
    void shouldCreateUserSuccessfully() {
        UserDomain user = createUserDomain();
        UserDomain createdUser = createUserDomain();
        createdUser.setId(1L);

        when(iUserGateway.createUser(any(UserDomain.class))).thenReturn(createdUser);

        UserDomain result = userCreateUseCase.execute(user);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(iUserGateway, times(1)).createUser(any(UserDomain.class));
    }

    @Test
    void shouldEncodePassword() {
        UserDomain user = createUserDomain();
        when(iUserGateway.createUser(any(UserDomain.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDomain result = userCreateUseCase.execute(user);

        assertNotEquals("password123", result.getPassword());
    }

    @Test
    void shouldSetClientRole() {
        UserDomain user = createUserDomain();
        when(iUserGateway.createUser(any(UserDomain.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDomain result = userCreateUseCase.execute(user);

        assertNotNull(result.getRole());
    }
}