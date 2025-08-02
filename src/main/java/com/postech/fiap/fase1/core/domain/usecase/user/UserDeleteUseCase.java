package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.validation.user.implementation.UserAllowedValidator;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDeleteUseCase {

    private final UserGateway userGateway;
    private final UserReadUseCase userReadUseCase;
    private final UserAllowedValidator userAllowedValidator;

    public void execute(Long idUser) {
        UserDomain userDomain = userReadUseCase.getById(idUser);
        userAllowedValidator.validate(userDomain);
        userGateway.delete(userDomain);
    }
}
