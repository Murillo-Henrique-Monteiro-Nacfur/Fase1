package com.postech.fiap.fase1.domain.validator;

import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.domain.model.UserDomain;
import com.postech.fiap.fase1.application.dto.auth.SessionDTO;
import com.postech.fiap.fase1.infrastructure.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private static final String PASSWORD_AND_CONFIRMATION_DO_NOT_MATCH = "Password and confirmation do not match";
    private static final String USER_WITH_THIS_LOGIN_ALREADY_EXISTS = "User with this login already exists";
    private static final String USER_WITH_THIS_EMAIL_ALREADY_EXISTS = "User with this email already exists";
    private static final String USER_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION = "User not authorized to perform this action";
    private final UserRepository userRepository;

    private void verifyUserPasswordAndConfirmation(UserDomain userDomain) {
        if (!userDomain.getPassword().equals(userDomain.getPasswordConfirmation())) {
            throw new ApplicationException(PASSWORD_AND_CONFIRMATION_DO_NOT_MATCH);
        }
    }

    private void verifyUserLoginAlreadyInUse(UserDomain userDomain) {
        userRepository.findByLogin(userDomain.getLogin()).filter(user -> !user.getId().equals(userDomain.getId())).ifPresent(user -> {
            throw new ApplicationException(USER_WITH_THIS_LOGIN_ALREADY_EXISTS);
        });
    }

    private void verifyUserEmailAlreadyInUse(UserDomain userDomain) {
        userRepository.findByEmail(userDomain.getLogin()).filter(user -> !user.getId().equals(userDomain.getId())).ifPresent(user -> {
            throw new ApplicationException(USER_WITH_THIS_EMAIL_ALREADY_EXISTS);
        });
    }

    private void validateUserLoginAndEmail(UserDomain userDomain) {
        verifyUserLoginAlreadyInUse(userDomain);
        verifyUserEmailAlreadyInUse(userDomain);
    }

    public void validateUserCreation(UserDomain userDomain) {
        verifyUserPasswordAndConfirmation(userDomain);
        validateUserLoginAndEmail(userDomain);
    }

    public void validateUserUpdateDetails(SessionDTO sessionDTO, UserDomain userDomain) {
        verifyUserLoggedIsAdminOrOwner(sessionDTO, userDomain.getId());
        validateUserLoginAndEmail(userDomain);
    }

    public void validateUserUpdatePassword(SessionDTO sessionDTO, UserDomain userDomain) {
        verifyUserLoggedIsAdminOrOwner(sessionDTO, userDomain.getId());
        verifyUserPasswordAndConfirmation(userDomain);
    }

    public void verifyUserLoggedIsAdminOrOwner(SessionDTO sessionDTO, Long id) {
        if (!sessionDTO.isAdmin() && !id.equals(sessionDTO.getUserId())) {
            throw new ApplicationException(USER_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION);
        }
    }
}
