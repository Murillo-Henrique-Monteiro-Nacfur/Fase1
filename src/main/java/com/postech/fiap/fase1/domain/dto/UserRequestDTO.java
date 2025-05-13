package com.postech.fiap.fase1.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordConfirmation;
    @NotBlank
    private LocalDate birthDate;
}
