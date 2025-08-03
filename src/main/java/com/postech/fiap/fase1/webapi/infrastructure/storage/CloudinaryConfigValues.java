package com.postech.fiap.fase1.webapi.infrastructure.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cloudinary")
@Getter
@Setter
public class CloudinaryConfigValues {
    private String cloudName;
    private String apiKey;
    private String apiSecret;
}
