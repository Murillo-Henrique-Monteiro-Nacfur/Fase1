package com.postech.fiap.fase1.core.controllers.user;

import com.postech.fiap.fase1.core.domain.usecase.user.UserDeleteUseCase;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.gateway.user.UserJpaGateway;
import com.postech.fiap.fase1.infrastructure.controller.user.UserControllerInterface;
import org.springframework.http.ResponseEntity;

public class UserDeleteCoreController {
    private final UserDeleteUseCase userDeleteUseCase;

    public UserDeleteCoreController(DataSource dataSource, SessionSource sessionSource) {
        var userJpaGateway = new UserJpaGateway(dataSource);
        var sessionGateway = new SessionGateway(sessionSource);
        this.userDeleteUseCase = new UserDeleteUseCase(userJpaGateway, sessionGateway);
    }

    public void delete(Long idUser) {
        userDeleteUseCase.execute(idUser);
    }

}
