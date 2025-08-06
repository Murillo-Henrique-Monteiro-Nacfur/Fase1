package com.postech.fiap.fase1.core.dto.user;

import com.postech.fiap.fase1.webapi.data.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String birthDate;
    private Role role;
}
