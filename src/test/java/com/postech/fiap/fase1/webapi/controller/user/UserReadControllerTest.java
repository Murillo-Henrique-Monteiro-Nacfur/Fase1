package com.postech.fiap.fase1.webapi.controller.user;

import com.postech.fiap.fase1.core.controllers.user.UserReadCoreController;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserReadControllerTest {

    @Mock
    private DataRepository dataRepository;
    @Mock
    private SessionRepository sessionRepository;
    @InjectMocks
    private UserReadController userReadController;

    @Test
    @DisplayName("Deve encontrar um usuário pelo ID e retornar status 200 (OK)")
    void findById_shouldDelegateToCoreControllerAndReturnOk() {
        Long userId = 1L;
        UserResponseDTO expectedResponse = UserResponseDTO.builder()
                .id(userId)
                .name("Test User")
                .login("testuser")
                .build();
        try (MockedConstruction<UserReadCoreController> mockedCoreController = mockConstruction(
                UserReadCoreController.class,
                (mock, context) ->
                        when(mock.findById(userId)).thenReturn(expectedResponse)
        )) {
            ResponseEntity<UserResponseDTO> responseEntity = userReadController.findById(userId);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);
            List<UserReadCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);
            UserReadCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock).findById(userId);
        }
    }

    @Test
    @DisplayName("Deve encontrar todos os usuários paginados e retornar status 200 (OK)")
    void findAll_shouldDelegateToCoreControllerAndReturnOk() {
        Pageable pageable = PageRequest.of(0, 10);
        UserResponseDTO userResponse = UserResponseDTO.builder().id(1L).name("Test User").build();
        Page<UserResponseDTO> expectedPage = new PageImpl<>(List.of(userResponse), pageable, 1);
        try (MockedConstruction<UserReadCoreController> mockedCoreController = mockConstruction(
                UserReadCoreController.class,
                (mock, context) ->
                        when(mock.findAll(pageable)).thenReturn(expectedPage)

        )) {
            ResponseEntity<Page<UserResponseDTO>> responseEntity = userReadController.findAll(pageable);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody().getContent()).hasSize(1);
            assertThat(responseEntity.getBody().getContent().getFirst()).isEqualTo(userResponse);
            List<UserReadCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);
            UserReadCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock).findAll(pageable);
        }
    }
}