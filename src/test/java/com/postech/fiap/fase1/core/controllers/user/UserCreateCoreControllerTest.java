package com.postech.fiap.fase1.core.controllers.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.domain.usecase.user.UserCreateUseCase;
import com.postech.fiap.fase1.core.dto.user.UserRequestDTO;
import com.postech.fiap.fase1.core.dto.user.UserResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.presenter.UserPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserCreateCoreControllerTest {
    private UserCreateCoreController controller;
    private UserCreateUseCase userCreateUseCase;
    private UserPresenter userPresenter;

    @BeforeEach
    void setUp() throws Exception {
        controller = new UserCreateCoreController(mock(DataSource.class));
        userCreateUseCase = mock(UserCreateUseCase.class);
        userPresenter = mock(UserPresenter.class);
        Field useCaseField = UserCreateCoreController.class.getDeclaredField("userCreateUseCase");
        useCaseField.setAccessible(true);
        useCaseField.set(controller, userCreateUseCase);
        Field presenterField = UserCreateCoreController.class.getDeclaredField("userPresenter");
        presenterField.setAccessible(true);
        presenterField.set(controller, userPresenter);
    }

    @Test
    void testCreateClient_Success() {
        UserRequestDTO requestDTO = mock(UserRequestDTO.class);
        UserDomain userDomain = mock(UserDomain.class);
        UserResponseDTO responseDTO = mock(UserResponseDTO.class);

        when(userPresenter.requestToDomain(requestDTO)).thenReturn(userDomain);
        when(userCreateUseCase.execute(userDomain)).thenReturn(userDomain);
        when(userPresenter.toResponseDTO(userDomain)).thenReturn(responseDTO);

        UserResponseDTO result = controller.createClient(requestDTO);
        assertEquals(responseDTO, result);
    }
}