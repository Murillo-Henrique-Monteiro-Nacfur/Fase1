package com.postech.fiap.fase1.core.gateway.storage;

import com.postech.fiap.fase1.core.dto.product.FileDTO;
import com.postech.fiap.fase1.core.gateway.StorageSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorageGatewayTest {

    @Mock private StorageSource storageSource;
    @Captor private ArgumentCaptor<String> fileNameCaptor;
    @Captor private ArgumentCaptor<byte[]> fileContentCaptor;
    private StorageGateway storageGateway;

    @BeforeEach
    void setUp() {
        storageGateway = StorageGateway.build(storageSource);
    }

    @Test
    @DisplayName("Deve construir uma instância de StorageGateway com sucesso")
    void build_shouldReturnNewInstance() {
        assertThat(storageGateway).isNotNull();
    }

    @Test
    @DisplayName("Deve chamar o upload do storageSource e retornar a URL de sucesso")
    void uploadFile_whenFileIsValid_shouldCallSourceAndReturnUrl() {
        byte[] fileContent = "dummy content".getBytes(); FileDTO fileDto = new FileDTO("test-image.jpg", fileContent); String expectedUrl = "https://storage.example.com/test-image.jpg";
        when(storageSource.uploadFile(anyString(), any(byte[].class))).thenReturn(expectedUrl);

        String actualUrl = storageGateway.uploadFile(fileDto);

        verify(storageSource, times(1)).uploadFile(fileNameCaptor.capture(), fileContentCaptor.capture());
        assertThat(fileNameCaptor.getValue()).isEqualTo("test-image.jpg");
        assertThat(fileContentCaptor.getValue()).isEqualTo(fileContent);

        assertThat(actualUrl).isEqualTo(expectedUrl);
    }

    @Test
    @DisplayName("Deve propagar a exceção se o storageSource falhar")
    void uploadFile_whenSourceThrowsException_shouldPropagateException() {
        FileDTO fileDto = new FileDTO("fail.txt", "content".getBytes());
        RuntimeException exceptionFromSource = new RuntimeException("Falha na conexão com o bucket S3");
        when(storageSource.uploadFile(anyString(), any(byte[].class))).thenThrow(exceptionFromSource);

        assertThatThrownBy(() -> storageGateway.uploadFile(fileDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Falha na conexão com o bucket S3");
    }

    @Test
    @DisplayName("Deve lançar NullPointerException se o FileDTO for nulo")
    void uploadFile_whenFileDtoIsNull_shouldThrowNullPointerException() {

        assertThatThrownBy(() -> storageGateway.uploadFile(null))
                .isInstanceOf(NullPointerException.class);
    }
}