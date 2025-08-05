package com.postech.fiap.fase1.webapi.utils;

import com.postech.fiap.fase1.core.dto.product.FileDTO;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class InputOutputFilesUtils {

    public FileDTO getFileDTO(MultipartFile file){
        try {
            return FileDTO.builder().fileName(file.getOriginalFilename()).file(file.getBytes()).build();
        } catch (Exception e) {
            throw new ApplicationException("Falha ao ler o arquivo");
        }
    }
}
