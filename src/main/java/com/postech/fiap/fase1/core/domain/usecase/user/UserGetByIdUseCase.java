package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import jakarta.validation.constraints.NotBlank;

public class UserGetByIdUseCase {

    private static final String USER_NOT_FOUND = "User not found";
    private final UserGateway userGateway;

    public UserGetByIdUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public UserDomain getUserByLogin(@NotBlank String login) {
        return userGateway.getUserByLogin(login).orElseThrow(() -> new ApplicationException(USER_NOT_FOUND));
    }
}
