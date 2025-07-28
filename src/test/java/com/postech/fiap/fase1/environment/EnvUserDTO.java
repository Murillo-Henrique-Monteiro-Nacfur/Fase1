package com.postech.fiap.fase1.environment;

import com.postech.fiap.fase1.application.dto.UserDTO;

import java.time.LocalDate;

public class EnvUserDTO {
    public static UserDTO getUserDTO() {
        return UserDTO.builder()
                .name("User name")
                .login("user login")
                .email("user email")
                .birthDate("" + LocalDate.now())
                .build();
    }
}
