package com.postech.fiap.fase1.core.dto.user;

import com.postech.fiap.fase1.core.dto.address.AddressDTO;
import com.postech.fiap.fase1.core.dto.address.AddressableDTO;
import com.postech.fiap.fase1.infrastructure.data.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@Setter
public class UserDTO implements AddressableDTO {
    private Long id;
    private String name;
    private String email;
    private String login;
    private LocalDate birthDate;
    private String password;
    private Role role;
    private List<AddressDTO> addresses;
}
