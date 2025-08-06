package com.postech.fiap.fase1.webapi.controller.user;

import com.postech.fiap.fase1.core.controllers.user.UserCreateAdminCoreController;
import com.postech.fiap.fase1.core.dto.user.UserRequestDTO;
import com.postech.fiap.fase1.core.dto.user.UserResponseDTO;
import com.postech.fiap.fase1.webapi.data.DataRepository;
import com.postech.fiap.fase1.webapi.infrastructure.session.SessionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCreateAdminControllerTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private UserCreateAdminController userCreateAdminController;

    @Test
    @DisplayName("Deve criar um usuário admin e retornar status 201 (Created)")
    void createAdmin_shouldDelegateToCoreControllerAndReturnCreated() {
        UserRequestDTO requestDTO = UserRequestDTO.builder()
                .name("New Admin")
                .login("newadmin")
                .email("admin@test.com")
                .password("password123")
                .build();

        UserResponseDTO expectedResponse = UserResponseDTO.builder()
                .id(99L)
                .name("New Admin")
                .login("newadmin")
                .email("admin@test.com")
                .build();

        try (MockedConstruction<UserCreateAdminCoreController> mockedCoreController = mockConstruction(
                UserCreateAdminCoreController.class,
                (mock, context) -> when(mock.createAdmin(any(UserRequestDTO.class))).thenReturn(expectedResponse)

        )) {
            ResponseEntity<UserResponseDTO> responseEntity = userCreateAdminController.createAdmin(requestDTO);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);

            List<UserCreateAdminCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);
            UserCreateAdminCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock).createAdmin(requestDTO);
        }
    }
}