package com.postech.fiap.fase1.webapi.infrastructure.storage;

import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CloudinaryConfigTest {

    @Mock
    private CloudinaryConfigValues cloudinaryConfigValues;

    @InjectMocks
    private CloudinaryConfig cloudinaryConfig;

    @Test
    @DisplayName("Deve configurar e retornar uma instância do Cloudinary corretamente")
    void cloudinary_shouldReturnConfiguredCloudinaryInstance() {
        String cloudName = "test-cloud-name";
        String apiKey = "test-api-key";
        String apiSecret = "test-api-secret";

        when(cloudinaryConfigValues.getCloudName()).thenReturn(cloudName);
        when(cloudinaryConfigValues.getApiKey()).thenReturn(apiKey);
        when(cloudinaryConfigValues.getApiSecret()).thenReturn(apiSecret);

        Cloudinary cloudinary = cloudinaryConfig.cloudinary();

        assertThat(cloudinary).isNotNull();
        assertThat(cloudinary.config.cloudName).isEqualTo(cloudName);
        assertThat(cloudinary.config.apiKey).isEqualTo(apiKey);
        assertThat(cloudinary.config.apiSecret).isEqualTo(apiSecret);

        verify(cloudinaryConfigValues).getCloudName();
        verify(cloudinaryConfigValues).getApiKey();
        verify(cloudinaryConfigValues).getApiSecret();
    }
}