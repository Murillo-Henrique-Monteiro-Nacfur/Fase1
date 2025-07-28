package com.postech.fiap.fase1.environment;

import com.postech.fiap.fase1.application.dto.UserRequestUpdateDetailsDTO;

import java.time.LocalDate;

public class EnvUserRequestUpdateDetailsDTO {
    public static UserRequestUpdateDetailsDTO getUserRequestUpdateDetailsDTO() {
        return UserRequestUpdateDetailsDTO
                .builder()
                .id(1L)
                .name("User name")
                .login("user login")
                .email("user email")
                .birthDate(LocalDate.now())
                .build();
    }
}
