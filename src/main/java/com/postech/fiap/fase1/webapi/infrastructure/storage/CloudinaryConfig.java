package com.postech.fiap.fase1.webapi.infrastructure.storage;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {

    private final CloudinaryConfigValues cloudinaryConfigValues;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudinaryConfigValues.getCloudName());
        config.put("api_key", cloudinaryConfigValues.getApiKey());
        config.put("api_secret", cloudinaryConfigValues.getApiSecret());

        return new Cloudinary(config);
    }
}
