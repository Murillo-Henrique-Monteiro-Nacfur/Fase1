package com.postech.fiap.fase1.domain.dto;

import com.postech.fiap.fase1.domain.model.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String birthDate;
    private Role role;
}
