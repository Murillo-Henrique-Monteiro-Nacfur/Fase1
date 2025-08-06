package com.postech.fiap.fase1.core.controllers.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.domain.usecase.user.UserUpdateDetailsUseCase;
import com.postech.fiap.fase1.core.domain.usecase.user.UserUpdatePasswordUseCase;
import com.postech.fiap.fase1.core.dto.user.UserRequestUpdateDetailsDTO;
import com.postech.fiap.fase1.core.dto.user.UserRequestUpdatePasswordDTO;
import com.postech.fiap.fase1.core.dto.user.UserResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.presenter.UserPresenter;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUpdateCoreControllerTest {
    @Mock
    private DataSource dataSource;
    @Mock
    private SessionSource sessionSource;
    @Mock
    private UserUpdateDetailsUseCase userUpdateDetailsUseCase;
    @Mock
    private UserUpdatePasswordUseCase userUpdatePasswordUseCase;
    @Mock
    private UserPresenter userPresenter;
    @Mock
    private UserRequestUpdateDetailsDTO userRequestUpdateDetailsDTO;
    @Mock
    private UserRequestUpdatePasswordDTO userRequestUpdatePasswordDTO;
    @Mock
    private UserResponseDTO userResponseDTO;
    @Mock
    private UserDomain userDomain;
    @InjectMocks
    private UserUpdateCoreController controller;

    @BeforeEach
    void setUp() throws Exception {
        controller = new UserUpdateCoreController(dataSource, sessionSource);
        var detailsField = UserUpdateCoreController.class.getDeclaredField("userUpdateDetailsUseCase");
        detailsField.setAccessible(true);
        detailsField.set(controller, userUpdateDetailsUseCase);
        var passwordField = UserUpdateCoreController.class.getDeclaredField("userUpdatePasswordUseCase");
        passwordField.setAccessible(true);
        passwordField.set(controller, userUpdatePasswordUseCase);
        var presenterField = UserUpdateCoreController.class.getDeclaredField("userPresenter");
        presenterField.setAccessible(true);
        presenterField.set(controller, userPresenter);
    }

    @Test
    void updateUserDetails_shouldReturnResponse_whenIdMatches() {
        Long idUser = 1L;
        when(userRequestUpdateDetailsDTO.getId()).thenReturn(idUser);
        when(userPresenter.requestUpdateDetailsToInput(userRequestUpdateDetailsDTO)).thenReturn(userDomain);
        when(userUpdateDetailsUseCase.execute(userDomain)).thenReturn(userDomain);
        when(userPresenter.toResponseDTO(userDomain)).thenReturn(userResponseDTO);

        UserResponseDTO result = controller.updateUserDetails(idUser, userRequestUpdateDetailsDTO);
        assertEquals(userResponseDTO, result);
    }

    @Test
    void updateUserDetails_shouldThrowException_whenIdDoesNotMatch() {
        Long idUser = 1L;
        when(userRequestUpdateDetailsDTO.getId()).thenReturn(2L);
        ApplicationException ex = assertThrows(ApplicationException.class, () -> controller.updateUserDetails(idUser, userRequestUpdateDetailsDTO));
        assertEquals("User is not the same as the one in the request", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getHttpStatus());
    }

    @Test
    void updateUserPassword_shouldReturnResponse_whenIdMatches() {
        Long idUser = 1L;
        when(userRequestUpdatePasswordDTO.getId()).thenReturn(idUser);
        when(userPresenter.requestUpdatePasswordToInput(userRequestUpdatePasswordDTO)).thenReturn(userDomain);
        when(userUpdatePasswordUseCase.execute(userDomain)).thenReturn(userDomain);
        when(userPresenter.toResponseDTO(userDomain)).thenReturn(userResponseDTO);

        UserResponseDTO result = controller.updateUserPassword(idUser, userRequestUpdatePasswordDTO);
        assertEquals(userResponseDTO, result);
    }

    @Test
    void updateUserPassword_shouldThrowException_whenIdDoesNotMatch() {
        Long idUser = 1L;
        when(userRequestUpdatePasswordDTO.getId()).thenReturn(2L);
        ApplicationException ex = assertThrows(ApplicationException.class, () -> controller.updateUserPassword(idUser, userRequestUpdatePasswordDTO));
        assertEquals("User is not the same as the one in the request", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getHttpStatus());
    }
}