package com.postech.fiap.fase1.core.domain.validation.user.implementation;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.domain.validation.session.ISessionValidation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAllowedValidatorTest {
    @Mock
    private ISessionValidation sessionValidation;

    @InjectMocks
    private UserAllowedValidator validator;

    @Test
    @DisplayName("Should call sessionValidation.validate with user id")
    void shouldValidateWithUserId() {
        UserDomain user = UserDomain.builder().id(10L).build();
        validator.validate(user);
        verify(sessionValidation).validate(10L);
    }

    @Test
    @DisplayName("Should call sessionValidation.validate with no args")
    void shouldValidateNoArgs() {
        validator.validate();
        verify(sessionValidation).validate();
    }
}