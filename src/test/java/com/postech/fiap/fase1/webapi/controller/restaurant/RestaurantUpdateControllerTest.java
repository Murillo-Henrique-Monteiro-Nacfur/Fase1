package com.postech.fiap.fase1.webapi.controller.restaurant;

import com.postech.fiap.fase1.core.controllers.restaurant.RestaurantUpdateCoreController;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantRequestUpdateDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantResponseDTO;
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
class RestaurantUpdateControllerTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private RestaurantUpdateController restaurantUpdateController;

    @Test
    @DisplayName("Deve atualizar um restaurante e retornar status 201 (Created)")
    void update_shouldDelegateToCoreControllerAndReturnCreated() {
        RestaurantRequestUpdateDTO requestDTO = RestaurantRequestUpdateDTO.builder()
                .id(1L)
                .name("Nome Atualizado")
                .build();

        RestaurantResponseDTO expectedResponse = RestaurantResponseDTO.builder()
                .id(1L)
                .name("Nome Atualizado")
                .build();

        try (MockedConstruction<RestaurantUpdateCoreController> mockedCoreController = mockConstruction(
                RestaurantUpdateCoreController.class,
                (mock, context) -> when(mock.update(requestDTO)).thenReturn(expectedResponse)
        )) {
            ResponseEntity<RestaurantResponseDTO> responseEntity = restaurantUpdateController.update(requestDTO);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);

            List<RestaurantUpdateCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);

            RestaurantUpdateCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock).update(requestDTO);
        }
    }
}