package com.postech.fiap.fase1.application.dto;

import com.postech.fiap.fase1.infrastructure.persistence.entity.Role;
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
    private AddressDTO address;
}
