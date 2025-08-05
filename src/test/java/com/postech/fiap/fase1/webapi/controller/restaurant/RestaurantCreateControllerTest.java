package com.postech.fiap.fase1.webapi.controller.restaurant;

import com.postech.fiap.fase1.core.controllers.restaurant.RestaurantCreateCoreController;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantRequestDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantCreateControllerTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private RestaurantCreateController restaurantCreateController;

    @Test
    @DisplayName("Deve criar um restaurante e retornar status 201 (Created)")
    void create_shouldDelegateToCoreControllerAndReturnCreated() {
        RestaurantRequestDTO requestDTO = RestaurantRequestDTO.builder()
                .name("Restaurante Teste")
                .cnpj("12345678000199")
                .build();

        RestaurantResponseDTO expectedResponse = RestaurantResponseDTO.builder()
                .id(1L)
                .name("Restaurante Teste")
                .cnpj("12345678000199")
                .build();

        try (MockedConstruction<RestaurantCreateCoreController> mockedCoreController = mockConstruction(
                RestaurantCreateCoreController.class,
                (mock, context) -> {
                    when(mock.create(any(RestaurantRequestDTO.class))).thenReturn(expectedResponse);
                }
        )) {
            ResponseEntity<RestaurantResponseDTO> responseEntity = restaurantCreateController.create(requestDTO);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);

            List<RestaurantCreateCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);

            RestaurantCreateCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock).create(requestDTO);
        }
    }
}