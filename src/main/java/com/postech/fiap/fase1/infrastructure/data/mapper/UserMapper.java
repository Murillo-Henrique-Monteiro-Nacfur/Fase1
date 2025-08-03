package com.postech.fiap.fase1.infrastructure.data.mapper;

import com.postech.fiap.fase1.core.dto.user.UserDTO;
import com.postech.fiap.fase1.infrastructure.data.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .login(userDTO.getLogin())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .birthDate(userDTO.getBirthDate())
                .role(userDTO.getRole())
                .build();
    }

    public User updateToEntity(UserDTO userDTO, User user) {
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setLogin(userDTO.getLogin());
        user.setBirthDate(userDTO.getBirthDate());
        return user;
    }

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .login(user.getLogin())
                .email(user.getEmail())
                .password(user.getPassword())
                .birthDate(user.getBirthDate())
                .role(user.getRole())
                // .addresses(isNull(user.getAddresses()) ? null : addressMapper.toDTO(user.getAddresses()))
                .build();
    }

    public Page<UserDTO> toDTO(Page<User> users) {
        return users.map(this::toDTO);
    }

}
