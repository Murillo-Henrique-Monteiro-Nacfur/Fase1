package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import com.postech.fiap.fase1.core.validation.session.SessionValidation;
import com.postech.fiap.fase1.core.validation.user.implementation.UserAllowedValidator;

public class UserDeleteUseCase {

    private final UserGateway userGateway;
    private final UserAllowedValidator userAllowedValidator;

    public UserDeleteUseCase(UserGateway userGateway, SessionGateway sessionGateway) {
        this.userGateway = userGateway;
        this.userAllowedValidator = new UserAllowedValidator(sessionGateway);
    }

    public void execute(Long idUser) {
        UserDomain userDomain = userGateway.getUserById(idUser);
        userAllowedValidator.validate(userDomain);
        userGateway.deleteUser(userDomain);
    }
}
