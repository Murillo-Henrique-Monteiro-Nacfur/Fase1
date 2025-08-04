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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserEmailAlreadyUsedValidatorTest {
    @Mock
    private IUserGateway iUserGateway;

    @InjectMocks
    private UserEmailAlreadyUsedValidator validator;

    @Test
    @DisplayName("Should throw exception when email already exists")
    void shouldThrowWhenEmailAlreadyExists() {
        UserDomain user = UserDomain.builder().id(1L).email("existing@email.com").build();
        when(iUserGateway.hasUserWithSameEmail(1L, "existing@email.com")).thenReturn(true);
        assertThatThrownBy(() -> validator.validate(user))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("User with this email already exists");
        verify(iUserGateway).hasUserWithSameEmail(1L, "existing@email.com");
    }

    @Test
    @DisplayName("Should not throw when email does not exist")
    void shouldNotThrowWhenEmailDoesNotExist() {
        UserDomain user = UserDomain.builder().id(2L).email("new@email.com").build();
        when(iUserGateway.hasUserWithSameEmail(2L, "new@email.com")).thenReturn(false);
        validator.validate(user);
        verify(iUserGateway).hasUserWithSameEmail(2L, "new@email.com");
    }
}