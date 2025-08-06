package com.postech.fiap.fase1.core.controllers.user;

import com.postech.fiap.fase1.core.domain.usecase.user.UserCreateAdminUseCase;
import com.postech.fiap.fase1.core.dto.user.UserRequestDTO;
import com.postech.fiap.fase1.core.dto.user.UserResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import com.postech.fiap.fase1.core.presenter.UserPresenter;

public class UserCreateAdminCoreController {
    private final UserCreateAdminUseCase userCreateAdminUseCase;
    private final UserPresenter userPresenter;

    public UserCreateAdminCoreController(DataSource dataSource, SessionSource sessionSource) {
        var userJpaGateway = UserGateway.build(dataSource);
        var sessionGateway = SessionGateway.build(sessionSource);

        this.userCreateAdminUseCase = UserCreateAdminUseCase.build(userJpaGateway, sessionGateway);
        this.userPresenter = new UserPresenter();
    }

    public UserResponseDTO createAdmin(UserRequestDTO userRequestDTO) {
        return userPresenter.toResponseDTO(userCreateAdminUseCase.execute(userPresenter.requestToDomain(userRequestDTO)));
    }
}