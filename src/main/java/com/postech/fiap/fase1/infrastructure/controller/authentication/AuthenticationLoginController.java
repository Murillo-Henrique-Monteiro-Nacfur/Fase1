package com.postech.fiap.fase1.infrastructure.controller.authentication;

import com.postech.fiap.fase1.core.controllers.AuthenticationLoginCoreController;
import com.postech.fiap.fase1.core.dto.auth.LoginRequestDTO;
import com.postech.fiap.fase1.core.dto.auth.LoginResponseDTO;
import com.postech.fiap.fase1.infrastructure.SecurityValues;
import com.postech.fiap.fase1.infrastructure.data.DataRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationLoginController {
    private final DataRepository dataRepository;

    private final SecurityValues securityValues;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        var authenticationLoginCoreController = new AuthenticationLoginCoreController(securityValues.getTokenSecret(), dataRepository);
        return ResponseEntity.ok(authenticationLoginCoreController.login(request));
    }

}
