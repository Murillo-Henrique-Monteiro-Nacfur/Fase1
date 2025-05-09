package com.postech.fiap.fase1.domain.controller;

import com.postech.fiap.fase1.configuration.exception.ApplicationException;
import com.postech.fiap.fase1.domain.dto.auth.LoginRequestDTO;
import com.postech.fiap.fase1.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO request) {
        try {
            var retono = authService.login(request);
            return ResponseEntity.ok(retono);
        } catch (Exception e) {
            throw new ApplicationException("Error during login");
        }
    }

}
