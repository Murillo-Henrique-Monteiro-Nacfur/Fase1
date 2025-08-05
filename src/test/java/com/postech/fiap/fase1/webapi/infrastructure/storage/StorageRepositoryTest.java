package com.postech.fiap.fase1.webapi.infrastructure.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StorageRepositoryTest {

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @InjectMocks
    private StorageRepository storageRepository;

    @BeforeEach
    void setUp() {
        when(cloudinary.uploader()).thenReturn(uploader);
    }

    @Test
    @DisplayName("Deve fazer upload do arquivo e retornar a URL com sucesso")
    void uploadFile_shouldReturnUrl_whenUploadIsSuccessful() throws IOException {
        String fileName = "test-file.jpg";
        byte[] fileContent = "dummy content".getBytes();
        String expectedUrl = "http://cloudinary.com/image/upload/v12345/checkup-images/some_id.jpg";
        Map<String, String> uploadResult = Map.of("url", expectedUrl);

        when(uploader.upload(any(byte[].class), anyMap())).thenReturn(uploadResult);

        String actualUrl = storageRepository.uploadFile(fileName, fileContent);

        assertThat(actualUrl).isEqualTo(expectedUrl);

        ArgumentCaptor<byte[]> fileCaptor = ArgumentCaptor.forClass(byte[].class);
        ArgumentCaptor<Map> paramsCaptor = ArgumentCaptor.forClass(Map.class);

        verify(uploader).upload(fileCaptor.capture(), paramsCaptor.capture());

        assertThat(fileCaptor.getValue()).isEqualTo(fileContent);
        Map<String, String> capturedParams = paramsCaptor.getValue();
        assertThat(capturedParams.get("folder")).isEqualTo("checkup-images");
        assertThat((String) capturedParams.get("public_id")).contains("test-file");
    }

    @Test
    @DisplayName("Deve lançar ApplicationException quando o upload falhar com IOException")
    void uploadFile_shouldThrowApplicationException_whenUploadFails() throws IOException {
        String fileName = "failing-file.png";
        byte[] fileContent = "fail content".getBytes();

        when(uploader.upload(any(byte[].class), anyMap())).thenThrow(new IOException("Upload failed"));

        assertThatThrownBy(() -> storageRepository.uploadFile(fileName, fileContent))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("Failed to upload image.")
                .satisfies(ex -> {
                    ApplicationException appEx = (ApplicationException) ex;
                    assertThat(appEx.getHttpStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }
}