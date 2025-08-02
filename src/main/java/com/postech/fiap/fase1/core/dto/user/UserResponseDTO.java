package com.postech.fiap.fase1.core.dto.user;

import com.postech.fiap.fase1.core.dto.address.AddressResponseDTO;
import com.postech.fiap.fase1.infrastructure.data.entity.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String birthDate;
    private Role role;
    private List<AddressResponseDTO> addresses;
}
