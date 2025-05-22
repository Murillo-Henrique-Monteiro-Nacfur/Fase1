package com.postech.fiap.fase1.domain.assembler;

import com.postech.fiap.fase1.domain.dto.UserDTO;
import com.postech.fiap.fase1.domain.model.User;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

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
                .adress(AdressAssembler.toDTO(user.getAdress()))
                .build();
    }

    public static Page<UserDTO> toDTO(Page<User> users) {
        return users.map(UserDisassembler::toDTO);
    }
}
