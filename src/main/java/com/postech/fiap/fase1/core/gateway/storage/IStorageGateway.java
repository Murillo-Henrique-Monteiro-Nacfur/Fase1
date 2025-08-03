package com.postech.fiap.fase1.core.gateway.storage;

import com.postech.fiap.fase1.core.dto.product.FileDTO;

public interface IStorageGateway {

    String uploadFile(FileDTO file);
}
