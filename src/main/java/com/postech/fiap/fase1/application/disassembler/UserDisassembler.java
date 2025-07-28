package com.postech.fiap.fase1.application.disassembler;

import com.postech.fiap.fase1.application.dto.UserDTO;
import com.postech.fiap.fase1.domain.model.UserDomain;
import com.postech.fiap.fase1.infrastructure.persistence.entity.User;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

@UtilityClass
public class UserDisassembler {

    public static UserDTO toDTO(UserDomain userDomain) {
        return UserDTO.builder()
                .id(userDomain.getId())
                .name(userDomain.getName())
                .email(userDomain.getEmail())
                .login(userDomain.getLogin())
                .role(userDomain.getRole())
                .birthDate(userDomain.getBirthDate().toString())
                //.address(isNull(user.getAddress()) ? null : AddressAssembler.toDTO(user.getAddress()))
                .build();
    }

    public static Page<UserDTO> toDTO(Page<UserDomain> users) {
        return users.map(UserDisassembler::toDTO);
    }
}
