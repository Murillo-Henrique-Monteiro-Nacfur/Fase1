package com.postech.fiap.fase1.environment;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.webapi.data.entity.Role;

public class EnvUserInputDTO {
    public static UserDomain getUserInputDTO(){
        return getUserInputDTO("password", "password");
    }

    public static UserDomain getUserInputDTO(String password, String passwordConfirmation){
        return getUserInputDTO(1L, password, passwordConfirmation);
    }

    public static UserDomain getUserInputDTOEWithNoID(){
        return getUserInputDTO(null, "password", "password");
    }
    private static UserDomain getUserInputDTO(Long id, String password, String passwordConfirmation){
        return UserDomain
                .builder()
                .id(id)
                .login("login")
                .email("email")
                .name("nome")
                .password(password)
                .passwordConfirmation(passwordConfirmation)
                .role(Role.CLIENT)
                .build();
    }

}
