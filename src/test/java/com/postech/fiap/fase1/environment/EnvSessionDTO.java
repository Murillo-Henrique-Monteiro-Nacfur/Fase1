package com.postech.fiap.fase1.environment;

import com.postech.fiap.fase1.domain.dto.auth.SessionDTO;
import com.postech.fiap.fase1.domain.model.Role;

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
