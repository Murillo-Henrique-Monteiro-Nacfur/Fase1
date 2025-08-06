package com.postech.fiap.fase1.webapi.controller.product;

import com.postech.fiap.fase1.core.controllers.product.ProductCreateCoreController;
import com.postech.fiap.fase1.core.dto.product.FileDTO;
import com.postech.fiap.fase1.core.dto.product.ProductRequestDTO;
import com.postech.fiap.fase1.core.dto.product.ProductResponseDTO;
import com.postech.fiap.fase1.webapi.data.DataRepository;
import com.postech.fiap.fase1.webapi.infrastructure.session.SessionRepository;
import com.postech.fiap.fase1.webapi.infrastructure.storage.StorageRepository;
import com.postech.fiap.fase1.webapi.utils.InputOutputFilesUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductCreateControllerTest {

    @Mock
    private DataRepository dataRepository;
    @Mock
    private InputOutputFilesUtils inputOutputFilesUtils;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private StorageRepository storageRepository;

    @InjectMocks
    private ProductCreateController productCreateController;

    @Test
    @DisplayName("Deve criar um produto e retornar status 201 (Created)")
    void createProduct_shouldDelegateToCoreControllerAndReturnCreated() {
        MultipartFile mockFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());
        ProductRequestDTO requestDTO = new ProductRequestDTO("Produto Teste", "Descrição", new BigDecimal("99.90"), "",1L);
        byte[] fileBytes = "test data".getBytes();
        var fileDto = FileDTO.builder().file(fileBytes).fileName("test data").build();
        ProductResponseDTO expectedResponse = ProductResponseDTO.builder()
                .id(1L)
                .name("Produto Teste")
                .price(new BigDecimal("99.90"))
                .build();


        when(inputOutputFilesUtils.getFileDTO(mockFile)).thenReturn(fileDto);
        try (MockedConstruction<ProductCreateCoreController> mockedCoreController = mockConstruction(
                ProductCreateCoreController.class,
                (mock, context) ->
                        when(mock.createProduct(any(ProductRequestDTO.class), any())).thenReturn(expectedResponse)
        )) {
            ResponseEntity<ProductResponseDTO> responseEntity = productCreateController.createProduct(mockFile, requestDTO);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);

            verify(inputOutputFilesUtils).getFileDTO(mockFile);

            List<ProductCreateCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);
        }
    }
}