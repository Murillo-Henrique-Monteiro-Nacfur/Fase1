package com.postech.fiap.fase1.webapi.infrastructure.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.postech.fiap.fase1.core.gateway.StorageSource;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageRepository implements StorageSource {

    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(String fileName, byte[] file) {
        try {
            Map<String, String> params = ObjectUtils.asMap(
                    "folder", "checkup-images",
                    "public_id", generateUniquePublicId(fileName)
            );
            Map<String, String> uploadResult = cloudinary.uploader().upload(file, params);
            return uploadResult.get("url");
        } catch (IOException e) {
            log.error("Falha no upload do arquivo para o Cloudinary: {}", fileName, e);
            throw new ApplicationException("Failed to upload image.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generateUniquePublicId(String originalFileName) {
        String dataFormatada = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
        String baseName = originalFileName;
        int lastDotIndex = originalFileName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            baseName = originalFileName.substring(0, lastDotIndex);
        }
        String sanitizedBaseName = baseName.replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9_.-]", "");

        return String.format("%s_%s", dataFormatada, sanitizedBaseName);
    }
}