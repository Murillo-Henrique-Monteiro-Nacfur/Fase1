package com.postech.fiap.fase1.core.gateway;

public interface StorageSource {

    String uploadFile(String fileName, byte[] file);
}
