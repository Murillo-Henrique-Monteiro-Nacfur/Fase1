package com.postech.fiap.fase1.webapi.controller.address;

import com.postech.fiap.fase1.core.controllers.address.AddressDeleteCoreController;
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
class AddressDeleteControllerTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private AddressDeleteController addressDeleteController;

    @Test
    @DisplayName("Deve deletar um endereço e retornar status 200 (OK)")
    void delete_shouldDelegateToCoreControllerAndReturnOk() {
        Long addressIdToDelete = 1L;

        try (MockedConstruction<AddressDeleteCoreController> mockedCoreController = mockConstruction(
                AddressDeleteCoreController.class
        )) {
            ResponseEntity<Void> responseEntity = addressDeleteController.delete(addressIdToDelete);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isNull();

            List<AddressDeleteCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);

            AddressDeleteCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock).delete(addressIdToDelete);
        }
    }
}