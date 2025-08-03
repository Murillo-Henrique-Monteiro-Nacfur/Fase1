package com.postech.fiap.fase1.core.controllers.user;

import com.postech.fiap.fase1.core.domain.usecase.user.UserReadUseCase;
import com.postech.fiap.fase1.core.dto.user.UserResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import com.postech.fiap.fase1.core.presenter.UserPresenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UserReadCoreController {
    private final UserReadUseCase userReadUseCase;
    private final UserPresenter userPresenter;

    public UserReadCoreController(DataSource dataSource, SessionSource sessionSource) {
        var userJpaGateway = UserGateway.build(dataSource);
        var sessionGateway = SessionGateway.build(sessionSource);
        this.userReadUseCase = new UserReadUseCase(userJpaGateway, sessionGateway);
        this.userPresenter = new UserPresenter();
    }

    public UserResponseDTO findById(Long id) {
        return userPresenter.toResponseDTO(userReadUseCase.getById(id));
    }

    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return userPresenter.toResponseDTO(userReadUseCase.getAllPaged(pageable));
    }

}
