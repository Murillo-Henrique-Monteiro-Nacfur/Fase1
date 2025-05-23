package com.postech.fiap.fase1.domain.dto;

import com.postech.fiap.fase1.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class UserInputDTO {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;
    private String passwordConfirmation;
    private LocalDate birthDate;
    private Role role;
}
