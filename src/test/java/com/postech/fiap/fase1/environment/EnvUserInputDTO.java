package com.postech.fiap.fase1.environment;

import com.postech.fiap.fase1.domain.dto.UserInputDTO;
import com.postech.fiap.fase1.domain.model.Role;

public class EnvUserInputDTO {
    public static UserInputDTO getUserInputDTO(){
        return getUserInputDTO("password", "password");
    }

    public static UserInputDTO getUserInputDTO(String password, String passwordConfirmation){
        return getUserInputDTO(1L, password, passwordConfirmation);
    }

    public static UserInputDTO getUserInputDTOEWithNoID(){
        return getUserInputDTO(null, "password", "password");
    }
    private static UserInputDTO getUserInputDTO(Long id, String password, String passwordConfirmation){
        return UserInputDTO
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
