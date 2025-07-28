package com.postech.fiap.fase1.application.assembler;

import com.postech.fiap.fase1.domain.model.UserDomain;
import com.postech.fiap.fase1.application.dto.UserRequestDTO;
import com.postech.fiap.fase1.application.dto.UserRequestUpdateDetailsDTO;
import com.postech.fiap.fase1.application.dto.UserRequestUpdatePasswordDTO;
import com.postech.fiap.fase1.infrastructure.persistence.entity.Role;
import com.postech.fiap.fase1.infrastructure.persistence.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserAssembler {

    public static UserDomain requestToInput(UserRequestDTO userRequestDTO, Role role) {
        return UserDomain.builder()
                .name(userRequestDTO.getName())
                .email(userRequestDTO.getEmail())
                .login(userRequestDTO.getLogin())
                .password(userRequestDTO.getPassword())
                .passwordConfirmation(userRequestDTO.getPasswordConfirmation())
                .birthDate(userRequestDTO.getBirthDate())
                .role(role)
                .build();
    }
    public static UserDomain requestUpdateDetailsToInput(UserRequestUpdateDetailsDTO userRequestUpdateDetailsDTO) {
        return UserDomain.builder()
                .id(userRequestUpdateDetailsDTO.getId())
                .name(userRequestUpdateDetailsDTO.getName())
                .email(userRequestUpdateDetailsDTO.getEmail())
                .login(userRequestUpdateDetailsDTO.getLogin())
                .birthDate(userRequestUpdateDetailsDTO.getBirthDate())
                .build();
    }
    public static UserDomain requestUpdatePasswordToInput(UserRequestUpdatePasswordDTO userRequestUpdatePasswordDTO) {
        return UserDomain.builder()
                .id(userRequestUpdatePasswordDTO.getId())
                .password(userRequestUpdatePasswordDTO.getPassword())
                .passwordConfirmation(userRequestUpdatePasswordDTO.getPasswordConfirmation())
                .build();
    }
    public static User inputToModel(UserDomain userDomain, String passwordEncoded) {
        return User.builder()
                .name(userDomain.getName())
                .email(userDomain.getEmail())
                .login(userDomain.getLogin())
                .password(passwordEncoded)
                .birthDate(userDomain.getBirthDate())
                .role(userDomain.getRole())
                .build();
    }

    public static void updateUserDetails(User user, UserDomain userDomain) {
        user.setName(userDomain.getName());
        user.setEmail(userDomain.getEmail());
        user.setLogin(userDomain.getLogin());
        user.setBirthDate(userDomain.getBirthDate());
    }

}
