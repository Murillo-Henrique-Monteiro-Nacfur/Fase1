package com.postech.fiap.fase1.webapi.controller.restaurant;

import com.postech.fiap.fase1.core.controllers.restaurant.RestaurantDeleteCoreController;
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
class RestaurantDeleteControllerTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private RestaurantDeleteController restaurantDeleteController;

    @Test
    @DisplayName("Deve deletar um restaurante e retornar status 200 (OK)")
    void delete_shouldDelegateToCoreControllerAndReturnOk() {
        Long restaurantIdToDelete = 123L;

        try (MockedConstruction<RestaurantDeleteCoreController> mockedCoreController = mockConstruction(
                RestaurantDeleteCoreController.class
        )) {
            ResponseEntity<Void> responseEntity = restaurantDeleteController.delete(restaurantIdToDelete);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isNull();

            List<RestaurantDeleteCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);

            RestaurantDeleteCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock, times(1)).delete(restaurantIdToDelete);
        }
    }
}