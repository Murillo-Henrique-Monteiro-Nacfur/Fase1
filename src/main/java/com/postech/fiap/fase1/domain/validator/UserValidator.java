package com.postech.fiap.fase1.domain.validator;

import com.postech.fiap.fase1.configuration.exception.ApplicationException;
import com.postech.fiap.fase1.configuration.session.ThreadLocalStorage;
import com.postech.fiap.fase1.domain.dto.UserInputDTO;
import com.postech.fiap.fase1.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateUserCreation(UserInputDTO userInputDTO) {
        verifyUserPasswordAndConfirmation(userInputDTO);
        validateUserLoginAndEmail(userInputDTO);
    }

    public void validateUserUpdateDetails(UserInputDTO userInputDTO) {
        verifyUserLoggedIsAdminOrOwner(userInputDTO.getId());
        validateUserLoginAndEmail(userInputDTO);
    }

    private void validateUserLoginAndEmail(UserInputDTO userInputDTO) {
        verifyUserLoginAlreadyInUse(userInputDTO);
        verifyUserEmailAlreadyInUse(userInputDTO);
    }

    public void verifyUserLoggedIsAdminOrOwner(Long id) {
        if (!ThreadLocalStorage.isAdmin() && !id.equals(ThreadLocalStorage.getUserId())) {//todo colocar thered na camada de controller e manda dto aqui para dentro
            throw new ApplicationException("User not authorized to perform this action");
        }
    }

    public void verifyUserPasswordAndConfirmation(UserInputDTO userInputDTO) {
        if (!userInputDTO.getPassword().equals(userInputDTO.getPasswordConfirmation())) {
            throw new ApplicationException("Password and confirmation do not match");
        }
    }

    private void verifyUserLoginAlreadyInUse(UserInputDTO userInputDTO) {
        userRepository.findByLogin(userInputDTO.getLogin()).filter(user -> !user.getId().equals(userInputDTO.getId())).ifPresent(user -> {
            throw new ApplicationException("User with this login already exists");
        });
    }

    private void verifyUserEmailAlreadyInUse(UserInputDTO userInputDTO) {
        userRepository.findByEmail(userInputDTO.getLogin()).filter(user -> !user.getId().equals(userInputDTO.getId())).ifPresent(user -> {
            throw new ApplicationException("User with this login already exists");
        });
    }
}
