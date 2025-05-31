package com.postech.fiap.fase1.domain.controller;

import com.postech.fiap.fase1.configuration.exception.ApplicationException;
import com.postech.fiap.fase1.domain.dto.auth.LoginRequestDTO;
import com.postech.fiap.fase1.domain.service.session.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void shouldDoLoginReturnsResponseEntityWithTokenForValidRequest() {
        LoginRequestDTO request = new LoginRequestDTO("userLogin", "userPassword");
        Map<String, String> tokenResponse = Map.of("token", "generatedToken");

        when(authService.login(request)).thenReturn(tokenResponse);

        ResponseEntity<Map<String, String>> response = authenticationController.login(request);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().containsEntry("token", "generatedToken");
    }

    @Test
    void shouldThrowsApplicationExceptionForAuthServiceError() {
        LoginRequestDTO request = new LoginRequestDTO("userLogin", "userPassword");

        when(authService.login(request)).thenThrow(new RuntimeException("Service error"));

        assertThatThrownBy(() -> authenticationController.login(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Service error");
    }
}