package com.postech.fiap.fase1.core.domain.usecase.session;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.domain.usecase.user.UserGetByIdUseCase;
import com.postech.fiap.fase1.core.dto.auth.LoginRequestDTO;
import com.postech.fiap.fase1.core.dto.auth.UserTokenBodyDTO;
import com.postech.fiap.fase1.webapi.data.entity.Role;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.webapi.utils.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthLoginUseCaseTest {

    @Mock
    private UserGetByIdUseCase userGetByIdUseCase;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private AuthLoginUseCase authLoginUseCase;

    @BeforeEach
    void setUp() {
        authLoginUseCase = new AuthLoginUseCase("test-token", userGetByIdUseCase, passwordEncoder, jwtUtils);
    }

    @Test
    void execute_success() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setLogin("testuser");
        loginRequestDTO.setPassword("password");

        UserDomain userDomain = UserDomain.builder()
                .id(1L)
                .name("Test User")
                .login("testuser")
                .email("test@example.com")
                .password("encodedPassword")
                .role(Role.ADMIN)
                .build();

        when(userGetByIdUseCase.getUserByLogin("testuser")).thenReturn(userDomain);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtUtils.buildJWTToken(anyString(), anyString(), any(Date.class), any(UserTokenBodyDTO.class))).thenReturn("test-token");

        String token = authLoginUseCase.execute(loginRequestDTO);

        assertEquals("test-token", token);
    }

    @Test
    void execute_invalidPassword() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setLogin("testuser");
        loginRequestDTO.setPassword("wrong-password");

        UserDomain userDomain = UserDomain.builder().build();
        userDomain.setPassword("encodedPassword");

        when(userGetByIdUseCase.getUserByLogin("testuser")).thenReturn(userDomain);
        when(passwordEncoder.matches("wrong-password", "encodedPassword")).thenReturn(false);

        assertThrows(ApplicationException.class, () -> authLoginUseCase.execute(loginRequestDTO));
    }

    @Test
    void execute_userNotFound() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setLogin("nonexistent-user");
        loginRequestDTO.setPassword("password");

        when(userGetByIdUseCase.getUserByLogin("nonexistent-user")).thenThrow(new RuntimeException());

        assertThrows(ApplicationException.class, () -> authLoginUseCase.execute(loginRequestDTO));
    }
}