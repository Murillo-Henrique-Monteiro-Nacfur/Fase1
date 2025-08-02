package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.validation.user.implementation.UserAllowedValidator;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public class UserReadUseCase {

    private final UserAllowedValidator userAllowedValidator;
    private final UserGateway userGateway;

    public UserReadUseCase(UserGateway userGateway, SessionGateway sessionGateway) {
        this.userAllowedValidator = new UserAllowedValidator(sessionGateway);
        this.userGateway = userGateway;
    }

    public UserDomain getById(Long idUser) {
        userAllowedValidator.validate(UserDomain.builder().id(idUser).build());
       return userGateway.getUserById(idUser);
    }

    public Page<UserDomain> getAllPaged(Pageable pageable) {
        userAllowedValidator.validate();
        return userGateway.getAllUserPaged(pageable);
    }

}
