package com.postech.fiap.fase1.webapi.infrastructure;

import com.postech.fiap.fase1.webapi.infrastructure.storage.CloudinaryConfigValues;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CloudinaryConfigValuesTest {

    @Test
    @DisplayName("Deve definir e obter os valores de configuração corretamente")
    void gettersAndSetters_shouldWorkCorrectly() {
        CloudinaryConfigValues configValues = new CloudinaryConfigValues();
        String cloudName = "my-cloud";
        String apiKey = "my-api-key";
        String apiSecret = "my-api-secret";

        configValues.setCloudName(cloudName);
        configValues.setApiKey(apiKey);
        configValues.setApiSecret(apiSecret);

        assertThat(configValues.getCloudName()).isEqualTo(cloudName);
        assertThat(configValues.getApiKey()).isEqualTo(apiKey);
        assertThat(configValues.getApiSecret()).isEqualTo(apiSecret);
    }
}