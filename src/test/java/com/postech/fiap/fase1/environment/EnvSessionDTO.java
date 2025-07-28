package com.postech.fiap.fase1.environment;

import com.postech.fiap.fase1.application.dto.auth.SessionDTO;
import com.postech.fiap.fase1.infrastructure.persistence.entity.Role;

public class EnvSessionDTO {
    public static SessionDTO getSessionDTOAdmin(){
        return getSessionDTO(1L, Role.ADMIN);
    }
    public static SessionDTO getSessionDTOClient(){
        return getSessionDTO(1L, Role.CLIENT);
    }

    public static SessionDTO getSessionDTO(Long id, Role role){
        return SessionDTO
                .builder()
                .userId(id)
                .userName("Uuer name")
                .userLogin("user login")
                .userEmail("user email")
                .userRole(role.name())
                .build();
    }
}
