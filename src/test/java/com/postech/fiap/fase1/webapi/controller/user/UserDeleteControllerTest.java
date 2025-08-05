package com.postech.fiap.fase1.webapi.controller.user;

import com.postech.fiap.fase1.core.controllers.user.UserDeleteCoreController;
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
class UserDeleteControllerTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private UserDeleteController userDeleteController;

    @Test
    @DisplayName("Deve deletar um usuário e retornar status 200 (OK)")
    void delete_shouldDelegateToCoreControllerAndReturnOk() {
        Long userIdToDelete = 42L;

        try (MockedConstruction<UserDeleteCoreController> mockedCoreController = mockConstruction(
                UserDeleteCoreController.class
        )) {
            ResponseEntity<Void> responseEntity = userDeleteController.delete(userIdToDelete);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isNull();

            List<UserDeleteCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);

            UserDeleteCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock, times(1)).delete(userIdToDelete);
        }
    }
}