package com.postech.fiap.fase1.domain.model;

import lombok.Getter;

@Getter
public enum Role {
    CLIENT("CLIENT"),
    ADMIN("ADMIN"),
    VIEWER("VIEWER");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public static Role fromString(String role) {
        for (Role r : Role.values()) {
            if (r.getDescription().equalsIgnoreCase(role)) {
                return r;
            }
        }
        throw new IllegalArgumentException("No enum constant " + Role.class.getCanonicalName() + "." + role);
    }
}
