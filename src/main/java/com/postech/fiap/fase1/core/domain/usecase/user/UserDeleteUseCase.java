package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import com.postech.fiap.fase1.core.validation.user.implementation.UserAllowedValidator;

public class UserDeleteUseCase {

    private final IUserGateway iUserGateway;
    private final UserAllowedValidator userAllowedValidator;

    public UserDeleteUseCase(IUserGateway iUserGateway, ISessionGateway sessionGateway) {
        this.iUserGateway = iUserGateway;
        this.userAllowedValidator = new UserAllowedValidator(sessionGateway);
    }

    public void execute(Long idUser) {
        UserDomain userDomain = iUserGateway.getUserById(idUser);
        userAllowedValidator.validate(userDomain);
        iUserGateway.deleteUser(userDomain);
    }
}
