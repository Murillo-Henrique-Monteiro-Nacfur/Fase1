package com.postech.fiap.fase1.core.controllers;

import com.postech.fiap.fase1.core.domain.usecase.session.AuthLoginUseCase;
import com.postech.fiap.fase1.core.dto.auth.LoginRequestDTO;
import com.postech.fiap.fase1.core.dto.auth.LoginResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.user.UserJpaGateway;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthenticationLoginCoreController {
    private final AuthLoginUseCase authLoginUseCase;

    public AuthenticationLoginCoreController(String tokenSecret, DataSource dataSource) {
        var userJpaGateway = new UserJpaGateway(dataSource);
        this.authLoginUseCase = new AuthLoginUseCase(tokenSecret, userJpaGateway);
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        return LoginResponseDTO.builder().token(authLoginUseCase.execute(request)).build();
    }

}
