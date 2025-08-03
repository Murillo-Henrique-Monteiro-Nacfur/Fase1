package com.postech.fiap.fase1.core.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class FileDTO {

    private String fileName;
    private byte[] file;
}
