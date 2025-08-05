package com.postech.fiap.fase1.webapi.controller.address;

import com.postech.fiap.fase1.core.controllers.address.AddressUpdateCoreController;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressUpdateControllerTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private AddressUpdateController addressUpdateController;

    @Test
    @DisplayName("Deve atualizar um endereço e retornar status 201 (Created)")
    void update_shouldDelegateToCoreControllerAndReturnCreated() {
        AddressRequestDTO requestDTO = AddressRequestDTO.builder()
                .id(1L)
                .street("Rua Atualizada")
                .build();

        AddressResponseDTO expectedResponse = AddressResponseDTO.builder()
                .id(1L)
                .street("Rua Atualizada")
                .build();

        try (MockedConstruction<AddressUpdateCoreController> mockedCoreController = mockConstruction(
                AddressUpdateCoreController.class,
                (mock, context) -> when(mock.update(requestDTO)).thenReturn(expectedResponse)
        )) {
            ResponseEntity<AddressResponseDTO> responseEntity = addressUpdateController.update(requestDTO);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);

            List<AddressUpdateCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);

            AddressUpdateCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock).update(requestDTO);
        }
    }
}