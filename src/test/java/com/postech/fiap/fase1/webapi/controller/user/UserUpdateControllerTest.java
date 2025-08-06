package com.postech.fiap.fase1.webapi.controller.user;

import com.postech.fiap.fase1.core.controllers.user.UserUpdateCoreController;
import com.postech.fiap.fase1.core.dto.user.UserRequestUpdateDetailsDTO;
import com.postech.fiap.fase1.core.dto.user.UserRequestUpdatePasswordDTO;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUpdateControllerTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private UserUpdateController userUpdateController;

    @Test
    @DisplayName("Deve atualizar os detalhes do usuário e retornar status 200 (OK)")
    void updateUserDetails_shouldDelegateToCoreControllerAndReturnOk() {
        Long userId = 1L;
        UserRequestUpdateDetailsDTO requestDTO = UserRequestUpdateDetailsDTO.builder().id(userId).name("Updated Name").email("updated@email.com").build();
        UserResponseDTO expectedResponse = UserResponseDTO.builder()
                .id(userId)
                .name("Updated Name")
                .email("updated@email.com")
                .build();

        try (MockedConstruction<UserUpdateCoreController> mockedCoreController = mockConstruction(
                UserUpdateCoreController.class,
                (mock, context) ->
                        when(mock.updateUserDetails(userId, requestDTO)).thenReturn(expectedResponse)

        )) {
            ResponseEntity<UserResponseDTO> responseEntity = userUpdateController.updateUserDetails(userId, requestDTO);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);

            List<UserUpdateCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);
            UserUpdateCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock).updateUserDetails(userId, requestDTO);
        }
    }

    @Test
    @DisplayName("Deve atualizar a senha do usuário e retornar status 201 (Created)")
    void updateUserPassword_shouldDelegateToCoreControllerAndReturnCreated() {
        Long userId = 1L;
        UserRequestUpdatePasswordDTO requestDTO = UserRequestUpdatePasswordDTO.builder().id(userId).password("password").passwordConfirmation("password").build();
        UserResponseDTO expectedResponse = UserResponseDTO.builder().id(userId).name("User").build();

        try (MockedConstruction<UserUpdateCoreController> mockedCoreController = mockConstruction(
                UserUpdateCoreController.class,
                (mock, context) ->
                        when(mock.updateUserPassword(userId, requestDTO)).thenReturn(expectedResponse)

        )) {
            ResponseEntity<UserResponseDTO> responseEntity = userUpdateController.updateUserPassword(userId, requestDTO);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);
            List<UserUpdateCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);
            UserUpdateCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock).updateUserPassword(userId, requestDTO);
        }
    }
}