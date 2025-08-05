package com.postech.fiap.fase1.webapi.controller.address;

import com.postech.fiap.fase1.core.controllers.address.AddressCreateCoreController;
import com.postech.fiap.fase1.core.dto.address.AddressRequestDTO;
import com.postech.fiap.fase1.core.dto.address.AddressResponseDTO;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mockConstruction;

@ExtendWith(MockitoExtension.class)
class AddressCreateControllerTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private AddressCreateController addressCreateController;

    @Test
    @DisplayName("Deve criar um endereço de usuário e retornar status 201 (Created)")
    void createUserAddress_shouldDelegateToCoreControllerAndReturnCreated() {
        Long userId = 1L;
        AddressRequestDTO requestDTO = AddressRequestDTO.builder().id(1L).street("Rua Teste").city("City").state("State").number("123").build();
        AddressResponseDTO expectedResponse = AddressResponseDTO.builder()
                .id(99L)
                .street("Rua Teste")
                .build();

        try (MockedConstruction<AddressCreateCoreController> mockedCoreController = mockConstruction(
                AddressCreateCoreController.class,
                (mock, context) -> when(mock.createUserAddress(any(AddressRequestDTO.class), anyLong())).thenReturn(expectedResponse)
        )) {
            ResponseEntity<AddressResponseDTO> responseEntity = addressCreateController.createUserAddress(requestDTO, userId);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);

            List<AddressCreateCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);

            AddressCreateCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock).createUserAddress(requestDTO, userId);
        }
    }

    @Test
    @DisplayName("Deve criar um endereço de restaurante e retornar status 201 (Created)")
    void createRestaurantAddress_shouldDelegateToCoreControllerAndReturnCreated() {
        Long restaurantId = 42L;
        AddressRequestDTO requestDTO = AddressRequestDTO.builder().id(1L).street("Rua Teste").city("City").state("State").number("123").build();
        AddressResponseDTO expectedResponse = AddressResponseDTO.builder()
                .id(100L)
                .street("Avenida Principal")
                .build();

        try (MockedConstruction<AddressCreateCoreController> mockedCoreController = mockConstruction(
                AddressCreateCoreController.class,
                (mock, context) -> when(mock.createRestaurantAddress(any(AddressRequestDTO.class), anyLong())).thenReturn(expectedResponse)
        )) {
            ResponseEntity<AddressResponseDTO> responseEntity = addressCreateController.createRestaurantAddress(requestDTO, restaurantId);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);

            List<AddressCreateCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);

            AddressCreateCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock).createRestaurantAddress(requestDTO, restaurantId);
        }
    }
}