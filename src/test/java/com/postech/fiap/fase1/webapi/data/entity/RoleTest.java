package com.postech.fiap.fase1.webapi.data.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class RoleTest {

    @ParameterizedTest
    @CsvSource({
            "CLIENT, Cliente do Restaurante",
            "ADMIN,  Dono do Restaurante",
            "VIEWER, VIEWER"
    })
    @DisplayName("Should return the correct description for each Role")
    void shouldReturnCorrectDescriptionForEachRole(Role role, String expectedDescription) {
        assertThat(role.getDescription()).isEqualTo(expectedDescription);
    }

    @ParameterizedTest
    @EnumSource(Role.class)
    @DisplayName("Should ensure all enum values are valid and not null")
    void shouldEnsureAllEnumValuesAreValid(Role role) {
        assertThat(role).isNotNull();
        assertThat(role.name()).isNotBlank();
    }

    @Test
    @DisplayName("Deve ser possível obter um Role a partir de uma String com valueOf")
    void shouldBeAbleToFindRoleByStringName() {
        String adminRoleName = "ADMIN";

        Role adminRole = Role.valueOf(adminRoleName);

        assertThat(adminRole).isNotNull().isEqualTo(Role.ADMIN);
    }
}