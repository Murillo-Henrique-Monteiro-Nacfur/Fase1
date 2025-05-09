package com.postech.fiap.fase1.domain.assembler;

import com.postech.fiap.fase1.domain.dto.UserInputDTO;
import com.postech.fiap.fase1.domain.dto.UserRequestDTO;
import com.postech.fiap.fase1.domain.model.Role;
import com.postech.fiap.fase1.domain.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserAssembler {

    public static UserInputDTO requestToInput(UserRequestDTO userRequestDTO, Role role) {
        return UserInputDTO.builder()
                .name(userRequestDTO.getName())
                .email(userRequestDTO.getEmail())
                .login(userRequestDTO.getLogin())
                .password(userRequestDTO.getPassword())
                .role(role)
                .build();
    }

    public static User inputToModel(UserInputDTO userInputDTO, String passwordEncoded) {
        return User.builder()
                .name(userInputDTO.getName())
                .email(userInputDTO.getEmail())
                .login(userInputDTO.getLogin())
                .password(passwordEncoded)
                .role(userInputDTO.getRole())
                .build();
    }
}
