package com.project.checkup.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CREATED_BY")
    private Long updatedBy;
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "CREATED_AT")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdBy = getCurrentUserId(); // Implementar a lógica para obter o usuário atual
        this.updatedBy = this.createdBy;  // Mesma pessoa cria inicialmente
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt; // Inicialmente, createdAt e updatedAt são iguais
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedBy = getCurrentUserId(); // Implementar a lógica para obter o usuário atual
        this.updatedAt = LocalDateTime.now();
    }

    private Long getCurrentUserId() {
        //TODO colcoar aqui a logica correta provavel ser parecido com esse abaixo Aqui você pode usar alguma classe de segurança, como Spring Security:
        // return SecurityContextHolder.getContext().getAuthentication().getName();
        // Ou algum outro método para identificar o usuário logado.
        return 1L; // Alterar conforme sua implementação.
    }
}