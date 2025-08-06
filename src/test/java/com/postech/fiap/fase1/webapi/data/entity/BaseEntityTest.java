package com.postech.fiap.fase1.webapi.data.entity;

import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BaseEntityTest {

    private ConcreteEntity testEntity;

    @Getter
    private static class ConcreteEntity extends BaseEntity {
    }

    @BeforeEach
    void setUp() {
        testEntity = new ConcreteEntity();
    }

    @Test
    @DisplayName("Deve definir createdAt e updatedAt com o mesmo valor ao chamar onCreate")
    void onCreate_shouldSetCreationAndModificationTimestamps() {
        assertThat(testEntity.getCreatedAt()).isNull();
        assertThat(testEntity.getUpdatedAt()).isNull();

        LocalDateTime timestampAntesDaChamada = LocalDateTime.now();

        testEntity.onCreate();

        LocalDateTime timestampDepoisDaChamada = LocalDateTime.now();

        assertThat(testEntity.getCreatedAt()).isNotNull();
        assertThat(testEntity.getUpdatedAt()).isNotNull();

        assertThat(testEntity.getCreatedAt()).isEqualTo(testEntity.getUpdatedAt());

        assertThat(testEntity.getCreatedAt()).isBetween(timestampAntesDaChamada, timestampDepoisDaChamada);
    }

    @Test
    @DisplayName("Deve atualizar apenas updatedAt e manter createdAt ao chamar onUpdate")
    void onUpdate_shouldOnlyUpdateModificationTimestamp() {
        testEntity.onCreate();
        LocalDateTime createdAtOriginal = testEntity.getCreatedAt();
        LocalDateTime updatedAtOriginal = testEntity.getUpdatedAt();

        assertThat(createdAtOriginal).isNotNull();
        assertThat(updatedAtOriginal).isNotNull();

        testEntity.onUpdate();

        assertThat(testEntity.getCreatedAt())
                .isEqualTo(createdAtOriginal);

        assertThat(testEntity.getUpdatedAt())
                .isNotNull()
                .isNotEqualTo(updatedAtOriginal)
                .isAfter(updatedAtOriginal);
    }
}