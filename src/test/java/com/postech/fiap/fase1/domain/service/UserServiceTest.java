package com.postech.fiap.fase1.domain.service;

import com.postech.fiap.fase1.configuration.exception.ApplicationException;
import com.postech.fiap.fase1.domain.dto.UserInputDTO;
import com.postech.fiap.fase1.domain.dto.auth.SessionDTO;
import com.postech.fiap.fase1.domain.model.User;
import com.postech.fiap.fase1.domain.repository.UserRepository;
import com.postech.fiap.fase1.domain.validator.UserValidator;
import com.postech.fiap.fase1.environment.EnvSessionDTO;
import com.postech.fiap.fase1.environment.EnvUser;
import com.postech.fiap.fase1.environment.EnvUserInputDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserValidator userValidator;
    @InjectMocks
    private UserService userService;

    @Test
    void shouldGetOneUserSuccessfully() {
        Long userId = 1L;
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTOClient();
        User user = EnvUser.getUserClient();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getOne(sessionDTO, userId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        verify(userValidator).verifyUserLoggedIsAdminOrOwner(sessionDTO, userId);
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldGetOneUser() {
        Long userId = 1L;
        User user = EnvUser.getUserAdmin();
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTOClient();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        var retorno = userService.getOne(sessionDTO, userId);
        assertThat(retorno).isNotNull().isEqualTo(user);
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowExceptionFindByIdWhenUserNotFound() {
        Long userId = 1L;
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTOClient();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getOne(sessionDTO, userId))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("User not found");

        verify(userRepository).findById(userId);
    }

    @Test
    void shouldGetUserByLoginWhenUser() {
        String login = "login";
        User user = EnvUser.getUserAdmin();
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));

        var retorno = userService.getUserByLogin(login);
        assertThat(retorno).isNotNull().isEqualTo(user);
        verify(userRepository).findByLogin(login);
    }

    @Test
    void shouldThrowExceptionGetUserByLoginWhenUserNotFound() {
        String login = "login";

        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserByLogin(login))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("User not found");

        verify(userRepository).findByLogin(any());
    }

    @Test
    void shouldCreateUserSuccessfully() {
        UserInputDTO userInputDTO = EnvUserInputDTO.getUserInputDTO();
        User user = EnvUser.getUserClient();

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.create(userInputDTO);

        assertThat(result).isNotNull();
        verify(userValidator).validateUserCreation(userInputDTO);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        Long userId = 1L;
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTOClient();
        User user = EnvUser.getUserClient();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.delete(sessionDTO, userId);

        verify(userValidator).verifyUserLoggedIsAdminOrOwner(sessionDTO, userId);
        verify(userRepository).delete(user);
    }

    @Test
    void shouldFindAllUsers() {
        Pageable pageable = mock(Pageable.class);
        List<User> users = EnvUser.getListUsers();
        Page<User> userPage = new PageImpl<>(users);

        when(userRepository.findAll(pageable)).thenReturn(userPage);

        Page<User> result = userService.findAll(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(users.size());
        verify(userRepository).findAll(pageable);
    }

    @Test
    void shouldUpdateUserDetailsSuccessfully() {
        // Arrange
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTOClient();
        UserInputDTO userInputDTO = EnvUserInputDTO.getUserInputDTO();
        User user = EnvUser.getUserClient();

        when(userRepository.findById(userInputDTO.getId())).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.updateDetails(sessionDTO, userInputDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(user.getId());
        verify(userValidator).validateUserUpdateDetails(sessionDTO, userInputDTO);
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingDetails() {
        // Arrange
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTOClient();
        UserInputDTO userInputDTO = EnvUserInputDTO.getUserInputDTO();

        when(userRepository.findById(userInputDTO.getId())).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.updateDetails(sessionDTO, userInputDTO))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("User not found");

        verify(userRepository).findById(userInputDTO.getId());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldUpdateUserPasswordSuccessfully() {
        // Arrange
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTOClient();
        UserInputDTO userInputDTO = EnvUserInputDTO.getUserInputDTO();
        User user = EnvUser.getUserClient();

        when(userRepository.findById(userInputDTO.getId())).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.encode(userInputDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.updatePassword(sessionDTO, userInputDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getPassword()).isEqualTo("encodedPassword");
        verify(userValidator).validateUserUpdatePassword(sessionDTO, userInputDTO);
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingPassword() {
        // Arrange
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTOClient();
        UserInputDTO userInputDTO = EnvUserInputDTO.getUserInputDTO();

        when(userRepository.findById(userInputDTO.getId())).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.updatePassword(sessionDTO, userInputDTO))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("User not found");

        verify(userRepository).findById(userInputDTO.getId());
        verifyNoMoreInteractions(userRepository);
    }
}