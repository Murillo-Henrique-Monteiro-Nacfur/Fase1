package com.postech.fiap.fase1.core.gateway.storage;

import com.postech.fiap.fase1.core.dto.product.FileDTO;
import com.postech.fiap.fase1.core.gateway.StorageSource;

public class StorageGateway implements IStorageGateway {

    private final StorageSource storageSource;

    private StorageGateway(StorageSource storageSource) {
        this.storageSource = storageSource;
    }

    public static StorageGateway build(StorageSource dataSource) {
        return new StorageGateway(dataSource);
    }

    public String uploadFile(FileDTO file) {
        return storageSource.uploadFile(file.getFileName(), file.getFile());
    }

}
