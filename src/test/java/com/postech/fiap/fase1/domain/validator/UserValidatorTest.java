package com.postech.fiap.fase1.domain.validator;

import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.domain.model.UserDomain;
import com.postech.fiap.fase1.application.dto.auth.SessionDTO;
import com.postech.fiap.fase1.infrastructure.persistence.entity.Role;
import com.postech.fiap.fase1.infrastructure.persistence.repository.UserRepository;
import com.postech.fiap.fase1.environment.EnvSessionDTO;
import com.postech.fiap.fase1.environment.EnvUserInputDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.postech.fiap.fase1.environment.EnvUser.getUserClient;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    private static final String PASSWORD_AND_CONFIRMATION_DO_NOT_MATCH = "Password and confirmation do not match";
    private static final String USER_WITH_THIS_LOGIN_ALREADY_EXISTS = "User with this login already exists";
    private static final String USER_WITH_THIS_EMAIL_ALREADY_EXISTS = "User with this email already exists";
    private static final String USER_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION = "User not authorized to perform this action";
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserValidator userValidator;

    @Test
    void shouldValidateUserCreation() {
        UserDomain userDomain = EnvUserInputDTO.getUserInputDTO();
        when(userRepository.findByLogin(any())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThatCode(() -> userValidator.validateUserCreation(userDomain))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionValidateUserCreationPassswordsDifferent() {
        UserDomain userDomain = EnvUserInputDTO.getUserInputDTO("password", "password2");

        assertThatThrownBy(() -> userValidator.validateUserCreation(userDomain))
                .isInstanceOf(ApplicationException.class)
                .hasMessage(PASSWORD_AND_CONFIRMATION_DO_NOT_MATCH);
    }

    @Test
    void shouldThrowExceptionValidateUserCreationLoginFound() {
        UserDomain userDomain = EnvUserInputDTO.getUserInputDTOEWithNoID();
        when(userRepository.findByLogin(any())).thenReturn(Optional.of(getUserClient()));

        assertThatThrownBy(() -> userValidator.validateUserCreation(userDomain))
                .isInstanceOf(ApplicationException.class)
                .hasMessage(USER_WITH_THIS_LOGIN_ALREADY_EXISTS);
    }

    @Test
    void shouldThrowExceptionValidateUserCreationEmailFound() {
        UserDomain userDomain = EnvUserInputDTO.getUserInputDTOEWithNoID();
        when(userRepository.findByLogin(any())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(getUserClient()));

        assertThatThrownBy(() -> userValidator.validateUserCreation(userDomain))
                .isInstanceOf(ApplicationException.class)
                .hasMessage(USER_WITH_THIS_EMAIL_ALREADY_EXISTS);
    }

    @Test
    void shouldValidateUserUserUpdateDetailsSessionAdmin() {
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTOAdmin();
        UserDomain userDomain = EnvUserInputDTO.getUserInputDTO();
        when(userRepository.findByLogin(any())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThatCode(() -> userValidator.validateUserUpdateDetails(sessionDTO, userDomain))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldValidateUserUserUpdateDetailsSessionSameUser() {
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTOClient();
        UserDomain userDomain = EnvUserInputDTO.getUserInputDTO();
        when(userRepository.findByLogin(any())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThatCode(() -> userValidator.validateUserUpdateDetails(sessionDTO, userDomain))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionValidateUserUpdateDetailsSessionDifferentUser() {
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTO(2L, Role.CLIENT);
        UserDomain userDomain = EnvUserInputDTO.getUserInputDTO();

        assertThatThrownBy(() -> userValidator.validateUserUpdateDetails(sessionDTO, userDomain))
                .isInstanceOf(ApplicationException.class)
                .hasMessage(USER_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION);
    }


    @Test
    void shouldValidateUserUpdatePasswordSessionAdmin() {
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTOClient();
        UserDomain userDomain = EnvUserInputDTO.getUserInputDTO();

        assertThatCode(() -> userValidator.validateUserUpdatePassword(sessionDTO, userDomain))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldValidateUserUpdatePasswordSessionSameUser() {
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTOClient();
        UserDomain userDomain = EnvUserInputDTO.getUserInputDTO();

        assertThatCode(() -> userValidator.validateUserUpdatePassword(sessionDTO, userDomain))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldValidateUserUpdatePasswordSessionDifferentUser() {
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTO(2L, Role.CLIENT);
        UserDomain userDomain = EnvUserInputDTO.getUserInputDTO();

        assertThatThrownBy(() -> userValidator.validateUserUpdatePassword(sessionDTO, userDomain))
                .isInstanceOf(ApplicationException.class)
                .hasMessage(USER_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION);
    }

    @Test
    void shouldValidateUserUpdatePasswordDifferentPassswords() {
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTOClient();
        UserDomain userDomain = EnvUserInputDTO.getUserInputDTO("password", "password2");

        assertThatThrownBy(() -> userValidator.validateUserUpdatePassword(sessionDTO, userDomain))
                .isInstanceOf(ApplicationException.class)
                .hasMessage(PASSWORD_AND_CONFIRMATION_DO_NOT_MATCH);
    }
}