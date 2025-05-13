package com.postech.fiap.fase1.domain.assembler;

import com.postech.fiap.fase1.domain.dto.UserInputDTO;
import com.postech.fiap.fase1.domain.dto.UserRequestDTO;
import com.postech.fiap.fase1.domain.dto.UserRequestUpdateDetailsDTO;
import com.postech.fiap.fase1.domain.dto.UserRequestUpdatePasswordDTO;
import com.postech.fiap.fase1.domain.model.Role;
import com.postech.fiap.fase1.domain.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserAssembler {

    public static UserInputDTO requestToInput(UserRequestDTO userRequestDTO, Role role) {
        return UserInputDTO.builder()
                .name(userRequestDTO.getName())
                .email(userRequestDTO.getEmail())
                .login(userRequestDTO.getLogin())
                .password(userRequestDTO.getPassword())
                .passwordConfirmation(userRequestDTO.getPasswordConfirmation())
                .birthDate(userRequestDTO.getBirthDate())
                .role(role)
                .build();
    }
    public static UserInputDTO requestUpdateDetailsToInput(UserRequestUpdateDetailsDTO userRequestUpdateDetailsDTO) {
        return UserInputDTO.builder()
                .id(userRequestUpdateDetailsDTO.getId())
                .name(userRequestUpdateDetailsDTO.getName())
                .email(userRequestUpdateDetailsDTO.getEmail())
                .login(userRequestUpdateDetailsDTO.getLogin())
                .birthDate(userRequestUpdateDetailsDTO.getBirthDate())
                .build();
    }
    public static UserInputDTO requestUpdatePasswordToInput(UserRequestUpdatePasswordDTO userRequestUpdatePasswordDTO) {
        return UserInputDTO.builder()
                .id(userRequestUpdatePasswordDTO.getId())
                .password(userRequestUpdatePasswordDTO.getPassword())
                .passwordConfirmation(userRequestUpdatePasswordDTO.getPasswordConfirmation())
                .build();
    }
    public static User inputToModel(UserInputDTO userInputDTO, String passwordEncoded) {
        return User.builder()
                .name(userInputDTO.getName())
                .email(userInputDTO.getEmail())
                .login(userInputDTO.getLogin())
                .password(passwordEncoded)
                .birthDate(userInputDTO.getBirthDate())
                .role(userInputDTO.getRole())
                .build();
    }

    public static void updateUserDetails(User user, UserInputDTO userInputDTO) {
        user.setName(userInputDTO.getName());
        user.setEmail(userInputDTO.getEmail());
        user.setLogin(userInputDTO.getLogin());
        user.setBirthDate(userInputDTO.getBirthDate());
    }

}
