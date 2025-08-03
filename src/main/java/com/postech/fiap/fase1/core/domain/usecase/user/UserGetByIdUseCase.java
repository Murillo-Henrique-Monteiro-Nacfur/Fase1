package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;

public class UserGetByIdUseCase {

    private static final String USER_NOT_FOUND = "User not found";
    private final IUserGateway iUserGateway;

    public UserGetByIdUseCase(IUserGateway iUserGateway) {
        this.iUserGateway = iUserGateway;
    }

    public UserDomain getUserByLogin(String login) {
        return iUserGateway.getUserByLogin(login).orElseThrow(() -> new ApplicationException(USER_NOT_FOUND));
    }
}
