package com.postech.fiap.fase1.webapi.controller.user;

import com.postech.fiap.fase1.core.controllers.user.UserCreateCoreController;
import com.postech.fiap.fase1.core.dto.user.UserRequestDTO;
import com.postech.fiap.fase1.core.dto.user.UserResponseDTO;
import com.postech.fiap.fase1.webapi.data.DataRepository;
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
class UserCreateControllerTest {

    @Mock
    private DataRepository dataRepository;

    @InjectMocks
    private UserCreateController userCreateController;

    @Test
    @DisplayName("Deve criar um usuário cliente e retornar status 201 (Created)")
    void createClient_shouldDelegateToCoreControllerAndReturnCreated() {
        UserRequestDTO requestDTO = UserRequestDTO.builder()
                .name("New Client")
                .login("newclient")
                .email("client@test.com")
                .password("password123")
                .build();

        UserResponseDTO expectedResponse = UserResponseDTO.builder()
                .id(100L)
                .name("New Client")
                .login("newclient")
                .email("client@test.com")
                .build();

        try (MockedConstruction<UserCreateCoreController> mockedCoreController = mockConstruction(
                UserCreateCoreController.class,
                (mock, context) ->
                    when(mock.createClient(any(UserRequestDTO.class))).thenReturn(expectedResponse)

        )) {
            ResponseEntity<UserResponseDTO> responseEntity = userCreateController.createClient(requestDTO);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);

            List<UserCreateCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);
            UserCreateCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock).createClient(requestDTO);
        }
    }
}