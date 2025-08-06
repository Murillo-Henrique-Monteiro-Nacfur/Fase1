package com.postech.fiap.fase1.core.domain.validation.user.implementation;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserPasswordValidatorTest {
    private UserPasswordValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UserPasswordValidator();
    }

    @Test
    void validate_shouldNotThrow_whenPasswordsMatch() {
        UserDomain user = mock(UserDomain.class);
        when(user.isPasswordDifferentFromConfirmation()).thenReturn(false);
        assertDoesNotThrow(() -> validator.validate(user));
    }

    @Test
    void validate_shouldThrowExceptionAndLog_whenPasswordsDoNotMatch() {
        UserDomain user = mock(UserDomain.class);
        when(user.isPasswordDifferentFromConfirmation()).thenReturn(true);
        ApplicationException ex = assertThrows(ApplicationException.class, () -> validator.validate(user));
        assertEquals("Password and confirmation do not match", ex.getMessage());
    }
}