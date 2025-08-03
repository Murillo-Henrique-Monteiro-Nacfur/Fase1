package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.validation.user.implementation.UserAllowedValidator;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UserReadUseCase {

    private final UserAllowedValidator userAllowedValidator;
    private final IUserGateway iUserGateway;

    public UserReadUseCase(IUserGateway iUserGateway, SessionGateway sessionGateway) {
        this.userAllowedValidator = new UserAllowedValidator(sessionGateway);
        this.iUserGateway = iUserGateway;
    }

    public UserDomain getById(Long idUser) {
        userAllowedValidator.validate(UserDomain.builder().id(idUser).build());
       return iUserGateway.getUserById(idUser);
    }

    public Page<UserDomain> getAllPaged(Pageable pageable) {
        userAllowedValidator.validate();
        return iUserGateway.getAllUserPaged(pageable);
    }

}
