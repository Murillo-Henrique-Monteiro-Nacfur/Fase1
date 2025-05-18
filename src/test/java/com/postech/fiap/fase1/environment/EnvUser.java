package com.postech.fiap.fase1.environment;

import com.postech.fiap.fase1.domain.model.Role;
import com.postech.fiap.fase1.domain.model.User;

import java.time.LocalDate;
import java.util.List;

public class EnvUser {
    public static User getUserAdmin() {
        return getUser(Role.ADMIN);
    }

    public static User getUserClient() {
        return getUser(Role.CLIENT);
    }

    private static User getUser(Role role) {
        return User
                .builder()
                .id(1L)
                .name("User name")
                .login("user login")
                .email("user email")
                .password("password")
                .birthDate(LocalDate.now())
                .role(role)
                .build();
    }

    public static List<User> getListUsers() {
        return List.of(getUserClient(), getUserAdmin());
    }
}
