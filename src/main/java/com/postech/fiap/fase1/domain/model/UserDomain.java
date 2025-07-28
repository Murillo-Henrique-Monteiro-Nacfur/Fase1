package com.postech.fiap.fase1.domain.model;

import com.postech.fiap.fase1.infrastructure.persistence.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class UserDomain implements AddressableDomain {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;
    private String passwordConfirmation;
    private LocalDate birthDate;
    private Role role;
    private List<AddressDomain> addresses;

    public boolean isPasswordDifferentFromConfirmation() {
        return !password.equals(passwordConfirmation);
    }

    public void setAdminRole() {
        this.role = Role.ADMIN;
    }

    public void setClientRole() {
        this.role = Role.CLIENT;
    }
}
