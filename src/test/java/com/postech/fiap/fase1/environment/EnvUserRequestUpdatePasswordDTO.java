package com.postech.fiap.fase1.environment;

import com.postech.fiap.fase1.domain.dto.UserRequestUpdatePasswordDTO;

public class EnvUserRequestUpdatePasswordDTO {
    public static UserRequestUpdatePasswordDTO getUserRequestUpdatePasswordDTO() {
        return UserRequestUpdatePasswordDTO
                .builder()
                .id(1L)
                .password("password")
                .passwordConfirmation("password")
                .build();
    }
}
