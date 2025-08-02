package com.postech.fiap.fase1.core.presenter;

import com.postech.fiap.fase1.core.dto.user.*;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.infrastructure.data.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class UserPresenter {

    private final AddressPresenter addressPresenter;
    public UserPresenter() {
        this.addressPresenter = new AddressPresenter();
    }


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
                .birthDate(userDomain.getBirthDate())
                .password(userDomain.getPassword())
                //.addresses(isNull(userDomain.getAddresses()) ? null : addressPresenter.toDTO(userDomain.getAddresses()))
                .build();
    }

    public UserResponseDTO toResponseDTO(UserDomain userDomain) {
        return UserResponseDTO.builder()
                .id(userDomain.getId())
                .name(userDomain.getName())
                .email(userDomain.getEmail())
                .login(userDomain.getLogin())
                .role(userDomain.getRole())
                .birthDate(userDomain.getBirthDate().toString())
                //.addresses(isNull(userDomain.getAddresses()) ? null : addressPresenter.toDTO(userDomain.getAddresses()))
                .build();
    }
    public Page<UserResponseDTO> toResponseDTO(Page<UserDomain> userDomains) {
        return userDomains.map(this::toResponseDTO);
    }

    public Page<UserDTO> toDTO(Page<UserDomain> users) {
        return users.map(this::toDTO);
    }

    public UserDomain toDomain(UserDTO userDTO) {
        return UserDomain.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .login(userDTO.getLogin())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .birthDate(userDTO.getBirthDate())
                .role(userDTO.getRole())
                //.addresses(isNull(userDTO.getAddresses()) ? null : addressMapper.toDomain(userDTO.getAddresses())) //todo
                .build();
    }

    public UserDTO updateToDTO(UserDomain userDomain, UserDTO userDTO) {
        userDTO.setName(userDomain.getName());
        userDTO.setEmail(userDomain.getEmail());
        userDTO.setLogin(userDomain.getLogin());
        userDTO.setBirthDate(userDomain.getBirthDate());
        return userDTO;
    }
}
