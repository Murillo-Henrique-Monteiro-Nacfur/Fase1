package com.postech.fiap.fase1.core.controllers.user;

import com.postech.fiap.fase1.core.domain.usecase.user.UserCreateUseCase;
import com.postech.fiap.fase1.core.dto.user.UserRequestDTO;
import com.postech.fiap.fase1.core.dto.user.UserResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import com.postech.fiap.fase1.core.presenter.UserPresenter;

public class UserCreateCoreController {
    private final UserCreateUseCase userCreateUseCase;
    private final UserPresenter userPresenter;

    public UserCreateCoreController(DataSource dataSource) {
        var userJpaGateway = UserGateway.build(dataSource);
        this.userCreateUseCase = new UserCreateUseCase(userJpaGateway);
        this.userPresenter = new UserPresenter();
    }

    public UserResponseDTO createClient(UserRequestDTO userRequestDTO) {
        return userPresenter.toResponseDTO(userCreateUseCase.execute(userPresenter.requestToDomain(userRequestDTO)));
    }
}