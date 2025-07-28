package com.postech.fiap.fase1.application.disassembler;

import com.postech.fiap.fase1.application.dto.UserDTO;
import com.postech.fiap.fase1.domain.model.UserDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDisassembler2 {
    public UserDTO toDTO(UserDomain user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .login(user.getLogin())
                .role(user.getRole())
                .birthDate(user.getBirthDate().toString())
                //.address(user.getAddresses().isEmpty() ? null : AddressAssembler.toDTO(user.getAddresses().getFirst()))
                .build();
    }

    public Page<UserDTO> toDTO(Page<UserDomain> users) {
        return users.map(this::toDTO);
    }
}
