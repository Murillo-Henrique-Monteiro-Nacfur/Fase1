package com.postech.fiap.fase1.infrastructure.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.postech.fiap.fase1.core.gateway.StorageSource;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StorageRepository implements StorageSource {

    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(String fileName, byte[] file) {
        try {
            Map<String, String> params = ObjectUtils.asMap(
                    "folder", "checkup-images",
                    "name", getFileName(fileName)
            );
            Map<String, String> uploadResult = cloudinary.uploader().upload(file, params);
            return uploadResult.get("url");
        } catch (IOException e) {
            // Handle upload error
            throw new ApplicationException("Failed to upload image.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getFileName(String fileName) {
        String dataFormatada = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
        return String.format("%s_%s",dataFormatada,fileName);
    }
}