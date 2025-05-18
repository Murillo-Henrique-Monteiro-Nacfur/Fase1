package com.postech.fiap.fase1.domain.service;

import com.postech.fiap.fase1.configuration.exception.ApplicationException;
import com.postech.fiap.fase1.domain.assembler.UserAssembler;
import com.postech.fiap.fase1.domain.dto.UserInputDTO;
import com.postech.fiap.fase1.domain.dto.auth.SessionDTO;
import com.postech.fiap.fase1.domain.model.User;
import com.postech.fiap.fase1.domain.repository.UserRepository;
import com.postech.fiap.fase1.domain.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String USER_NOT_FOUND = "User not found";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ApplicationException(USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public User getOne(SessionDTO sessionDTO, Long id) {
        userValidator.verifyUserLoggedIsAdminOrOwner(sessionDTO, id);
        return findById(id);
    }

    @Transactional(readOnly = true)
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(
                () -> new ApplicationException(USER_NOT_FOUND));
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User create(UserInputDTO userInputDTO) {
        userValidator.validateUserCreation(userInputDTO);
        User user = UserAssembler.inputToModel(userInputDTO, passwordEncoder.encode(userInputDTO.getPassword()));
        return userRepository.save(user);
    }

    public User updateDetails(SessionDTO sessionDTO, UserInputDTO userInputDTO) {
        User user = findById(userInputDTO.getId());
        userValidator.validateUserUpdateDetails(sessionDTO, userInputDTO);
        UserAssembler.updateUserDetails(user, userInputDTO);
        return userRepository.save(user);
    }

    public User updatePassword(SessionDTO sessionDTO, UserInputDTO userInputDTO) {
        User user = findById(userInputDTO.getId());
        userValidator.validateUserUpdatePassword(sessionDTO, userInputDTO);
        user.setPassword(passwordEncoder.encode(userInputDTO.getPassword()));
        return userRepository.save(user);
    }

    public void delete(SessionDTO sessionDTO, Long idUser) {
        User user = findById(idUser);
        userValidator.verifyUserLoggedIsAdminOrOwner(sessionDTO, user.getId());
        userRepository.delete(user);
    }
}
