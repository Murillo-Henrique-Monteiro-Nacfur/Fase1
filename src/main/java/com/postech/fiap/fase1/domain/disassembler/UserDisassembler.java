package com.postech.fiap.fase1.domain.disassembler;

import com.postech.fiap.fase1.domain.assembler.AddressAssembler;
import com.postech.fiap.fase1.domain.dto.UserDTO;
import com.postech.fiap.fase1.domain.model.User;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

import static java.util.Objects.isNull;

@UtilityClass
public class UserDisassembler {

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .login(user.getLogin())
                .role(user.getRole())
                .birthDate(user.getBirthDate().toString())
                .address(isNull(user.getAddress()) ? null : AddressAssembler.toDTO(user.getAddress()))
                .build();
    }

    public static Page<UserDTO> toDTO(Page<User> users) {
        return users.map(UserDisassembler::toDTO);
    }
}
