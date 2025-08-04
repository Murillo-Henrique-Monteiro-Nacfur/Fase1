package com.postech.fiap.fase1.core.controllers.authentication;

import com.postech.fiap.fase1.core.domain.usecase.session.AuthLoginUseCase;
import com.postech.fiap.fase1.core.dto.auth.LoginRequestDTO;
import com.postech.fiap.fase1.core.dto.auth.LoginResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationLoginCoreControllerTest {

    @Mock
    private AuthLoginUseCase authLoginUseCase;
    @InjectMocks
    private AuthenticationLoginCoreController controller;

    @BeforeEach
    void setUp() {
        controller = new AuthenticationLoginCoreController(authLoginUseCase);
    }

    @Test
    void testLogin() {
        LoginRequestDTO requestDTO = new LoginRequestDTO("testuser", "password");
        String expectedToken = "fake-jwt-token";

        when(authLoginUseCase.execute(any(LoginRequestDTO.class))).thenReturn(expectedToken);

        LoginResponseDTO response = controller.login(requestDTO);

        assertNotNull(response);
        assertEquals(expectedToken, response.getToken());
    }
}