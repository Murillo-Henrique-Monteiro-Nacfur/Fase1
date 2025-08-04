package com.postech.fiap.fase1.core.domain.validation.user.implementation;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserLoginAlreadyUsedValidatorTest {
    @Mock
    private IUserGateway iUserGateway;

    @InjectMocks
    private UserLoginAlreadyUsedValidator validator;

    @Test
    @DisplayName("Should throw exception when login already exists")
    void shouldThrowWhenLoginAlreadyExists() {
        UserDomain user = UserDomain.builder().id(1L).login("existingLogin").build();
        when(iUserGateway.hasUserWithSameLogin(1L, "existingLogin")).thenReturn(true);
        assertThatThrownBy(() -> validator.validate(user))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("User with this login already exists");
        verify(iUserGateway).hasUserWithSameLogin(1L, "existingLogin");
    }

    @Test
    @DisplayName("Should not throw when login does not exist")
    void shouldNotThrowWhenLoginDoesNotExist() {
        UserDomain user = UserDomain.builder().id(2L).login("newLogin").build();
        when(iUserGateway.hasUserWithSameLogin(2L, "newLogin")).thenReturn(false);
        validator.validate(user);
        verify(iUserGateway).hasUserWithSameLogin(2L, "newLogin");
    }
}