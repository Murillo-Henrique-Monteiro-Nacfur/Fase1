package com.postech.fiap.fase1.infrastructure.persistence.entity;

import lombok.Getter;

@Getter
public enum Role {
    CLIENT("Cliente do Restaurante"),
    ADMIN("Dono do Restaurante"),
    VIEWER("VIEWER");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}
