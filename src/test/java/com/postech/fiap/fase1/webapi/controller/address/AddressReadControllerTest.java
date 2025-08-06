package com.postech.fiap.fase1.webapi.controller.address;

import com.postech.fiap.fase1.core.controllers.address.AddressReadCoreController;
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
class AddressReadControllerTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private AddressReadController addressReadController;

    @Test
    @DisplayName("Deve encontrar um endereço pelo ID e retornar status 200 (OK)")
    void findById_shouldDelegateToCoreControllerAndReturnOk() {
        Long addressId = 1L;
        AddressResponseDTO expectedResponse = AddressResponseDTO.builder()
                .id(addressId)
                .street("Rua Teste")
                .city("Cidade Teste")
                .build();

        try (MockedConstruction<AddressReadCoreController> mockedCoreController = mockConstruction(
                AddressReadCoreController.class,
                (mock, context) -> when(mock.findById(addressId)).thenReturn(expectedResponse)
        )) {
            ResponseEntity<AddressResponseDTO> responseEntity = addressReadController.findById(addressId);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);

            List<AddressReadCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);

            AddressReadCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock).findById(addressId);
        }
    }

    @Test
    @DisplayName("Deve encontrar todos os endereços paginados e retornar status 200 (OK)")
    void findAllPaged_shouldDelegateToCoreControllerAndReturnOk() {
        Pageable pageable = PageRequest.of(0, 10);
        AddressResponseDTO addressResponse = AddressResponseDTO.builder().id(1L).street("Rua Teste").build();
        Page<AddressResponseDTO> expectedPage = new PageImpl<>(List.of(addressResponse), pageable, 1);

        try (MockedConstruction<AddressReadCoreController> mockedCoreController = mockConstruction(
                AddressReadCoreController.class,
                (mock, context) -> when(mock.findAllPaged(pageable)).thenReturn(expectedPage)
        )) {
            ResponseEntity<Page<AddressResponseDTO>> responseEntity = addressReadController.findAllPaged(pageable);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody().getContent()).hasSize(1);
            assertThat(responseEntity.getBody().getContent().getFirst()).isEqualTo(addressResponse);

            List<AddressReadCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);

            AddressReadCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock).findAllPaged(pageable);
        }
    }
}