package com.postech.fiap.fase1.domain.validator;

import com.postech.fiap.fase1.configuration.exception.ApplicationException;
import com.postech.fiap.fase1.domain.dto.UserInputDTO;
import com.postech.fiap.fase1.domain.dto.auth.SessionDTO;
import com.postech.fiap.fase1.domain.repository.UserRepository;
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

    private void verifyUserPasswordAndConfirmation(UserInputDTO userInputDTO) {
        if (!userInputDTO.getPassword().equals(userInputDTO.getPasswordConfirmation())) {
            throw new ApplicationException(PASSWORD_AND_CONFIRMATION_DO_NOT_MATCH);
        }
    }

    private void verifyUserLoginAlreadyInUse(UserInputDTO userInputDTO) {
        userRepository.findByLogin(userInputDTO.getLogin()).filter(user -> !user.getId().equals(userInputDTO.getId())).ifPresent(user -> {
            throw new ApplicationException(USER_WITH_THIS_LOGIN_ALREADY_EXISTS);
        });
    }

    private void verifyUserEmailAlreadyInUse(UserInputDTO userInputDTO) {
        userRepository.findByEmail(userInputDTO.getLogin()).filter(user -> !user.getId().equals(userInputDTO.getId())).ifPresent(user -> {
            throw new ApplicationException(USER_WITH_THIS_EMAIL_ALREADY_EXISTS);
        });
    }

    private void validateUserLoginAndEmail(UserInputDTO userInputDTO) {
        verifyUserLoginAlreadyInUse(userInputDTO);
        verifyUserEmailAlreadyInUse(userInputDTO);
    }

    public void validateUserCreation(UserInputDTO userInputDTO) {
        verifyUserPasswordAndConfirmation(userInputDTO);
        validateUserLoginAndEmail(userInputDTO);
    }

    public void validateUserUpdateDetails(SessionDTO sessionDTO, UserInputDTO userInputDTO) {
        verifyUserLoggedIsAdminOrOwner(sessionDTO, userInputDTO.getId());
        validateUserLoginAndEmail(userInputDTO);
    }

    public void validateUserUpdatePassword(SessionDTO sessionDTO, UserInputDTO userInputDTO) {
        verifyUserLoggedIsAdminOrOwner(sessionDTO, userInputDTO.getId());
        verifyUserPasswordAndConfirmation(userInputDTO);
    }

    public void verifyUserLoggedIsAdminOrOwner(SessionDTO sessionDTO, Long id) {
        if (!sessionDTO.isAdmin() && !id.equals(sessionDTO.getUserId())) {
            throw new ApplicationException(USER_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION);
        }
    }
}
