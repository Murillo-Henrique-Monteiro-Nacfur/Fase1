package com.postech.fiap.fase1.core.validation.user.implementation;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.validation.session.SessionUserAllowedValidator;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAdminAllowedValidatorTest {
    @Mock
    private SessionUserAllowedValidator sessionUserAllowedValidator;
    @Mock
    private ISessionGateway iSessionGateway;

    @InjectMocks
    private UserAdminAllowedValidator userAdminAllowedValidator;

    @Test
    void create_shouldReturnANewUserAdminAllowedValidator() {
        var newUserAdminAllowedValidator = UserAdminAllowedValidator.build(iSessionGateway);

        Assertions.assertNotNull(newUserAdminAllowedValidator);
    }

    @Test
    @DisplayName("Deve chamar a validação de sessão com sucesso quando a sessão for válida")
    void validate_whenSessionIsValid_shouldDelegateToSessionValidation() {
        doNothing().when(sessionUserAllowedValidator).validate();
        UserDomain dummyUserDomain = UserDomain.builder().build();

        userAdminAllowedValidator.validate(dummyUserDomain);

        verify(sessionUserAllowedValidator).validate();
        verifyNoMoreInteractions(sessionUserAllowedValidator);
    }

    @Test
    @DisplayName("Deve propagar a exceção se a validação de sessão falhar")
    void validate_whenSessionIsInvalid_shouldPropagateException() {
        ApplicationException expectedException = new ApplicationException("Acesso não autorizado");
        doThrow(expectedException).when(sessionUserAllowedValidator).validate();
        UserDomain dummyUserDomain = UserDomain.builder().build();

        assertThatThrownBy(() -> userAdminAllowedValidator.validate(dummyUserDomain))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("Acesso não autorizado");

        verify(sessionUserAllowedValidator).validate();
    }
}