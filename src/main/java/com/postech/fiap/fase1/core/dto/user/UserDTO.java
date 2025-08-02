package com.postech.fiap.fase1.core.dto.user;

import com.postech.fiap.fase1.core.dto.address.AddressDTO;
import com.postech.fiap.fase1.infrastructure.data.entity.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String birthDate;
    private Role role;
    private List<AddressDTO> addresses;
}
