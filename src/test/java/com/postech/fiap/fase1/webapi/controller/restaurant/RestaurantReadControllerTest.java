package com.postech.fiap.fase1.webapi.controller.restaurant;

import com.postech.fiap.fase1.core.controllers.restaurant.RestaurantReadCoreController;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantResponseDTO;
import com.postech.fiap.fase1.webapi.data.DataRepository;
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
class RestaurantReadControllerTest {

    @Mock
    private DataRepository dataRepository;

    @InjectMocks
    private RestaurantReadController restaurantReadController;

    @Test
    @DisplayName("Deve encontrar um restaurante pelo ID e retornar status 200 (OK)")
    void findById_shouldDelegateToCoreControllerAndReturnOk() {
        Long restaurantId = 1L;
        RestaurantResponseDTO expectedResponse = RestaurantResponseDTO.builder()
                .id(restaurantId)
                .name("Restaurante Teste")
                .build();

        try (MockedConstruction<RestaurantReadCoreController> mockedCoreController = mockConstruction(
                RestaurantReadCoreController.class,
                (mock, context) -> when(mock.findById(restaurantId)).thenReturn(expectedResponse)
        )) {
            ResponseEntity<RestaurantResponseDTO> responseEntity = restaurantReadController.findById(restaurantId);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);

            List<RestaurantReadCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);

            RestaurantReadCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock).findById(restaurantId);
        }
    }

    @Test
    @DisplayName("Deve encontrar todos os restaurantes paginados e retornar status 200 (OK)")
    void getAllPaged_shouldDelegateToCoreControllerAndReturnOk() {
        Pageable pageable = PageRequest.of(0, 5);
        RestaurantResponseDTO restaurantResponse = RestaurantResponseDTO.builder().id(1L).name("Restaurante Teste").build();
        Page<RestaurantResponseDTO> expectedPage = new PageImpl<>(List.of(restaurantResponse), pageable, 1);

        try (MockedConstruction<RestaurantReadCoreController> mockedCoreController = mockConstruction(
                RestaurantReadCoreController.class,
                (mock, context) -> when(mock.getAllPaged(pageable)).thenReturn(expectedPage)
        )) {
            ResponseEntity<Page<RestaurantResponseDTO>> responseEntity = restaurantReadController.getAllPaged(pageable);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody().getContent()).hasSize(1);
            assertThat(responseEntity.getBody().getContent().getFirst()).isEqualTo(restaurantResponse);

            List<RestaurantReadCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);

            RestaurantReadCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock).getAllPaged(pageable);
        }
    }
}