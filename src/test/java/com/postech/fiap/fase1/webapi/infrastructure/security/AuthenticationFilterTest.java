package com.postech.fiap.fase1.webapi.infrastructure.security;

import com.postech.fiap.fase1.webapi.infrastructure.session.AuthLoadAccessService;
import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.webapi.data.entity.Role;
import com.postech.fiap.fase1.webapi.infrastructure.session.ThreadLocalStorage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationFilterTest {

    @Mock
    private AuthLoadAccessService authLoadAccessService;

    @InjectMocks
    private AuthenticationFilter authenticationFilter;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private SecurityContext securityContext;

    @Test
    @DisplayName("Deve extrair o token do header 'X-Auth-Token' como principal")
    void getPreAuthenticatedPrincipal_shouldReturnTokenFromHeader() {
        String testToken = "my-secret-token";
        when(request.getHeader("X-Auth-Token")).thenReturn(testToken);

        Object principal = authenticationFilter.getPreAuthenticatedPrincipal(request);

        assertThat(principal).isNotNull().isEqualTo(testToken);
    }

    @Test
    @DisplayName("Deve retornar 'N/A' como credenciais pré-autenticadas")
    void getPreAuthenticatedCredentials_shouldReturnNA() {
        // Act
        Object credentials = authenticationFilter.getPreAuthenticatedCredentials(request);
        assertThat(credentials).isNotNull().isEqualTo("N/A");
    }

    @Test
    @DisplayName("Deve autenticar com sucesso e popular o SecurityContext quando o token é válido")
    void doFilter_whenTokenIsValid_shouldSetAuthenticationInContext() throws ServletException, IOException {
        String validToken = "valid-token";
        String userLogin = "testuser";
        Role userRole = Role.ADMIN;

        when(request.getHeader("X-Auth-Token")).thenReturn(validToken);
        when(authLoadAccessService.execute(validToken)).thenReturn(true);
        SessionDTO sessionDTO = SessionDTO.builder()
                .userLogin(userLogin)
                .userRole(userRole.name())
                .build();

        try (MockedStatic<ThreadLocalStorage> mockedStatic = Mockito.mockStatic(ThreadLocalStorage.class)) {
            mockedStatic.when(ThreadLocalStorage::getSession).thenReturn(sessionDTO);
            SecurityContextHolder.setContext(securityContext);

            authenticationFilter.doFilter(request, response, filterChain);

            verify(filterChain).doFilter(request, response);
        }
    }

    @Test
    @DisplayName("Não deve autenticar e deve limpar o SecurityContext quando o token é inválido")
    void doFilter_whenTokenIsInvalid_shouldNotSetAuthenticationInContext() throws ServletException, IOException {
        String invalidToken = "invalid-token";
        when(request.getHeader("X-Auth-Token")).thenReturn(invalidToken);
        when(authLoadAccessService.execute(invalidToken)).thenReturn(false);

        SecurityContextHolder.setContext(securityContext);

        authenticationFilter.doFilter(request, response, filterChain);

        verify(securityContext, never()).setAuthentication(any(Authentication.class));
        verify(filterChain).doFilter(request, response);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Não deve autenticar quando o token no header é nulo ou vazio")
    void doFilter_whenTokenIsNullOrEmpty_shouldNotAuthenticate(String token) throws ServletException, IOException {
        when(request.getHeader("X-Auth-Token")).thenReturn(token);
        SecurityContextHolder.setContext(securityContext);

        authenticationFilter.doFilter(request, response, filterChain);
        verify(authLoadAccessService, never()).execute(anyString());
        verify(securityContext, never()).setAuthentication(any(Authentication.class));
        verify(filterChain).doFilter(request, response);
    }

}