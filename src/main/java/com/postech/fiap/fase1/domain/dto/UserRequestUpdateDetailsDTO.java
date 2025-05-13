package com.postech.fiap.fase1.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class UserRequestUpdateDetailsDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String login;
    @NotBlank
    private LocalDate birthDate;
}
