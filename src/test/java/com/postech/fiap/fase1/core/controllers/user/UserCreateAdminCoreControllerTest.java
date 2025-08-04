package com.postech.fiap.fase1.core.controllers.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.domain.usecase.user.UserCreateAdminUseCase;
import com.postech.fiap.fase1.core.dto.user.UserRequestDTO;
import com.postech.fiap.fase1.core.dto.user.UserResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.presenter.UserPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserCreateAdminCoreControllerTest {
    private UserCreateAdminCoreController controller;
    private UserCreateAdminUseCase userCreateAdminUseCase;
    private UserPresenter userPresenter;

    @BeforeEach
    void setUp() throws Exception {
        controller = new UserCreateAdminCoreController(mock(DataSource.class), mock(SessionSource.class));
        userCreateAdminUseCase = mock(UserCreateAdminUseCase.class);
        userPresenter = mock(UserPresenter.class);
        Field useCaseField = UserCreateAdminCoreController.class.getDeclaredField("userCreateAdminUseCase");
        useCaseField.setAccessible(true);
        useCaseField.set(controller, userCreateAdminUseCase);
        Field presenterField = UserCreateAdminCoreController.class.getDeclaredField("userPresenter");
        presenterField.setAccessible(true);
        presenterField.set(controller, userPresenter);
    }

    @Test
    void testCreateAdmin_Success() {
        UserRequestDTO requestDTO = mock(UserRequestDTO.class);
        UserDomain userDomain = mock(UserDomain.class);
        UserResponseDTO responseDTO = mock(UserResponseDTO.class);

        when(userPresenter.requestToDomain(requestDTO)).thenReturn(userDomain);
        when(userCreateAdminUseCase.execute(userDomain)).thenReturn(userDomain);
        when(userPresenter.toResponseDTO(userDomain)).thenReturn(responseDTO);

        UserResponseDTO result = controller.createAdmin(requestDTO);
        assertEquals(responseDTO, result);
    }
}