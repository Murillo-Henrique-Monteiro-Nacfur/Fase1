package com.postech.fiap.fase1.webapi.controller.product;

import com.postech.fiap.fase1.core.controllers.product.ProductDeleteCoreController;
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
class ProductDeleteControllerTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private ProductDeleteController productDeleteController;

    @Test
    @DisplayName("Deve deletar um produto e retornar status 200 (OK)")
    void deleteProduct_shouldDelegateToCoreControllerAndReturnOk() {
        Long productIdToDelete = 42L;

        try (MockedConstruction<ProductDeleteCoreController> mockedCoreController = mockConstruction(
                ProductDeleteCoreController.class
        )) {
            ResponseEntity<Object> responseEntity = productDeleteController.deleteProduct(productIdToDelete);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isNull();

            List<ProductDeleteCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);

            ProductDeleteCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock, times(1)).delete(productIdToDelete);
        }
    }
}