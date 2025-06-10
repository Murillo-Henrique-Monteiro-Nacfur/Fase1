package com.postech.fiap.fase1.domain.controller;

import com.postech.fiap.fase1.configuration.exception.ApplicationException;
import com.postech.fiap.fase1.configuration.session.SessionService;
import com.postech.fiap.fase1.controller.UserController;
import com.postech.fiap.fase1.domain.dto.UserDTO;
import com.postech.fiap.fase1.domain.dto.UserRequestDTO;
import com.postech.fiap.fase1.domain.dto.auth.SessionDTO;
import com.postech.fiap.fase1.domain.model.User;
import com.postech.fiap.fase1.domain.service.UserService;
import com.postech.fiap.fase1.environment.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.postech.fiap.fase1.environment.EnvUser.getListUsers;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private static final String USER_IS_NOT_THE_SAME_AS_THE_ONE_IN_THE_REQUEST = "User is not the same as the one in the request";
    @Mock
    private UserService userService;

    @Mock
    private SessionService sessionService;

    @InjectMocks
    private UserController userController;

    @Test
    void shouldFindUserById() {
        Long userId = 1L;
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTOClient();
        User user = EnvUser.getUserClient();
        when(sessionService.getSessionDTO()).thenReturn(sessionDTO);
        when(userService.getOne(sessionDTO, userId)).thenReturn(user);

        ResponseEntity<UserDTO> response = userController.findById(userId);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(sessionService).getSessionDTO();
        verify(userService).getOne(sessionDTO, userId);
    }

    @Test
    void shouldFindAllUsers() {
        Pageable pageable = mock(Pageable.class);
        var listUsers = getListUsers();
        Page<User> userPage = new PageImpl<>(listUsers);
        when(userService.findAll(pageable)).thenReturn(userPage);

        ResponseEntity<Page<UserDTO>> response = userController.findAll(pageable);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getSize()).isEqualTo(userPage.getSize());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userService).findAll(pageable);
    }

    @Test
    void shouldCreateClient() {
        UserRequestDTO userRequestDTO = EnvUserRequestDTO.getUserRequestDTO();
        User user = EnvUser.getUserClient();
        when(userService.create(any())).thenReturn(user);

        ResponseEntity<UserDTO> response = userController.createClient(userRequestDTO);

        assertThat(response.getBody()).isNotNull();
        var responseBody = response.getBody();
        assertThat(responseBody.getId()).isEqualTo(user.getId());
        assertThat(responseBody.getName()).isEqualTo(user.getName());
        assertThat(responseBody.getLogin()).isEqualTo(user.getLogin());
        assertThat(responseBody.getEmail()).isEqualTo(user.getEmail());
        assertThat(responseBody.getRole().name()).isEqualTo(user.getRole().name());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(userService).create(any());
    }

    @Test
    void shouldCreateAdmin() {
        UserRequestDTO userRequestDTO = EnvUserRequestDTO.getUserRequestDTO();
        User user = EnvUser.getUserAdmin();
        when(userService.create(any())).thenReturn(user);

        ResponseEntity<UserDTO> response = userController.createAdmin(userRequestDTO);

        assertThat(response.getBody()).isNotNull();
        var responseBody = response.getBody();
        assertThat(responseBody.getId()).isEqualTo(user.getId());
        assertThat(responseBody.getName()).isEqualTo(user.getName());
        assertThat(responseBody.getLogin()).isEqualTo(user.getLogin());
        assertThat(responseBody.getEmail()).isEqualTo(user.getEmail());
        assertThat(responseBody.getRole().name()).isEqualTo(user.getRole().name());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(userService).create(any());
    }

    @Test
    void shouldUpdateUserDetails() {
        var userRequestUpdateDetailsDTO = EnvUserRequestUpdateDetailsDTO.getUserRequestUpdateDetailsDTO();
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTOClient();
        User user = EnvUser.getUserAdmin();
        when(sessionService.getSessionDTO()).thenReturn(sessionDTO);
        when(userService.updateDetails(any(), any())).thenReturn(user);

        ResponseEntity<UserDTO> response = userController.updateUserDetails(1L, userRequestUpdateDetailsDTO);

        assertThat(response.getBody()).isNotNull();
        var responseBody = response.getBody();
        assertThat(responseBody.getId()).isEqualTo(user.getId());
        assertThat(responseBody.getName()).isEqualTo(user.getName());
        assertThat(responseBody.getLogin()).isEqualTo(user.getLogin());
        assertThat(responseBody.getEmail()).isEqualTo(user.getEmail());
        assertThat(responseBody.getRole().name()).isEqualTo(user.getRole().name());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(userService).updateDetails(any(), any());
    }

    @Test
    void shouldThrowUpdateUserDetailsIdsDifferents() {
        var userRequestUpdateDetailsDTO = EnvUserRequestUpdateDetailsDTO.getUserRequestUpdateDetailsDTO();

        assertThatThrownBy(() -> userController.updateUserDetails(2L, userRequestUpdateDetailsDTO)).isInstanceOf(ApplicationException.class).hasMessage(USER_IS_NOT_THE_SAME_AS_THE_ONE_IN_THE_REQUEST);
    }

    @Test
    void shouldUpdateUserPassword() {
        var userRequestUpdatePasswordDTO = EnvUserRequestUpdatePasswordDTO.getUserRequestUpdatePasswordDTO();
        SessionDTO sessionDTO = EnvSessionDTO.getSessionDTOClient();
        User user = EnvUser.getUserAdmin();
        when(sessionService.getSessionDTO()).thenReturn(sessionDTO);
        when(userService.updatePassword(any(), any())).thenReturn(user);

        ResponseEntity<UserDTO> response = userController.updateUserPassword(1L, userRequestUpdatePasswordDTO);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(userService).updatePassword(any(), any());
    }

    @Test
    void shouldThrowUpdateUserPasswordIdsDifferents() {
        var userRequestUpdatePasswordDTO = EnvUserRequestUpdatePasswordDTO.getUserRequestUpdatePasswordDTO();

        assertThatThrownBy(() -> userController.updateUserPassword(2L, userRequestUpdatePasswordDTO)).isInstanceOf(ApplicationException.class).hasMessage(USER_IS_NOT_THE_SAME_AS_THE_ONE_IN_THE_REQUEST);
    }

    @Test
    void shouldDeleteUser() {
        Long userId = 1L;
        SessionDTO sessionDTO = new SessionDTO();
        when(sessionService.getSessionDTO()).thenReturn(sessionDTO);

        ResponseEntity<Void> response = userController.delete(userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(sessionService).getSessionDTO();
        verify(userService).delete(sessionDTO, userId);
    }

}