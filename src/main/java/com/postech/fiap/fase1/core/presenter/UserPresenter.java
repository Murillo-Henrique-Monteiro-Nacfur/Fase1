package com.postech.fiap.fase1.core.presenter;

import com.postech.fiap.fase1.core.dto.user.UserDTO;
import com.postech.fiap.fase1.core.dto.user.UserRequestDTO;
import com.postech.fiap.fase1.core.dto.user.UserRequestUpdateDetailsDTO;
import com.postech.fiap.fase1.core.dto.user.UserRequestUpdatePasswordDTO;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class UserPresenter {

    private final AddressPresenter addressPresenter;

    public UserDomain requestToDomain(UserRequestDTO userRequestDTO) {
        return UserDomain.builder()
                .name(userRequestDTO.getName())
                .email(userRequestDTO.getEmail())
                .login(userRequestDTO.getLogin())
                .password(userRequestDTO.getPassword())
                .passwordConfirmation(userRequestDTO.getPasswordConfirmation())
                .birthDate(userRequestDTO.getBirthDate())
                .build();
    }

    public UserDomain requestUpdateDetailsToInput(UserRequestUpdateDetailsDTO userRequestUpdateDetailsDTO) {
        return UserDomain.builder()
                .id(userRequestUpdateDetailsDTO.getId())
                .name(userRequestUpdateDetailsDTO.getName())
                .email(userRequestUpdateDetailsDTO.getEmail())
                .login(userRequestUpdateDetailsDTO.getLogin())
                .birthDate(userRequestUpdateDetailsDTO.getBirthDate())
                .build();
    }

    public UserDomain requestUpdatePasswordToInput(UserRequestUpdatePasswordDTO userRequestUpdatePasswordDTO) {
        return UserDomain.builder()
                .id(userRequestUpdatePasswordDTO.getId())
                .password(userRequestUpdatePasswordDTO.getPassword())
                .passwordConfirmation(userRequestUpdatePasswordDTO.getPasswordConfirmation())
                .build();
    }

    public UserDTO toDTO(UserDomain userDomain) {
        return UserDTO.builder()
                .id(userDomain.getId())
                .name(userDomain.getName())
                .email(userDomain.getEmail())
                .login(userDomain.getLogin())
                .role(userDomain.getRole())
                .birthDate(userDomain.getBirthDate().toString())
                .addresses(isNull(userDomain.getAddresses()) ? null : addressPresenter.toDTO(userDomain.getAddresses()))
                .build();
    }

    public Page<UserDTO> toDTO(Page<UserDomain> users) {
        return users.map(this::toDTO);
    }
}
