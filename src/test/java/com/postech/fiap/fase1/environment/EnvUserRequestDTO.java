package com.postech.fiap.fase1.environment;

import com.postech.fiap.fase1.domain.dto.UserRequestDTO;

import java.time.LocalDate;

public class EnvUserRequestDTO {
    public static UserRequestDTO getUserRequestDTO() {
        return UserRequestDTO
                .builder()
                .name("User name")
                .login("user login")
                .email("user email")
                .password("password")
                .passwordConfirmation("password")
                .birthDate(LocalDate.now())
                .build();
    }
}
