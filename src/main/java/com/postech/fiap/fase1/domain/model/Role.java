package com.postech.fiap.fase1.domain.model;

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
