package com.postech.fiap.fase1.core.controllers.authentication;

import com.postech.fiap.fase1.core.domain.usecase.session.AuthLoginUseCase;
import com.postech.fiap.fase1.core.dto.auth.LoginRequestDTO;
import com.postech.fiap.fase1.core.dto.auth.LoginResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;

public class AuthenticationLoginCoreController {
    private final AuthLoginUseCase authLoginUseCase;

    public AuthenticationLoginCoreController(String tokenSecret, DataSource dataSource) {
        var userJpaGateway = UserGateway.build(dataSource);
        this.authLoginUseCase = new AuthLoginUseCase(tokenSecret, userJpaGateway);
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        return LoginResponseDTO.builder().token(authLoginUseCase.execute(request)).build();
    }

}
