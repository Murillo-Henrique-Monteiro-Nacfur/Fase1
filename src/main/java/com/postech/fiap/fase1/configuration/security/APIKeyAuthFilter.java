package com.postech.fiap.fase1.configuration.security;

import com.postech.fiap.fase1.configuration.session.ThreadLocalStorage;
import com.postech.fiap.fase1.domain.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
public class APIKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final String PRINCIPAL_REQUEST_HEADER = "X-Auth-Token";
    private static final String ROLE_PREFIX = "ROLE_";
    private final AuthService authService;

    @Lazy
    public APIKeyAuthFilter(AuthService authService) {
        this.authService = authService;
        setAuthenticationManager(createAuthenticationManager());
    }

    private AuthenticationManager createAuthenticationManager() {
        return (authentication -> {
            String token = (String) authentication.getPrincipal();
            if (token == null || token.isEmpty() || !authService.checkAndLoadAccess(token)) {
                return new UsernamePasswordAuthenticationToken(null, null);
            }

            String login = ThreadLocalStorage.getUserLogin();
            String role = ThreadLocalStorage.getUserRole();
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + role));
            return new UsernamePasswordAuthenticationToken(login, null, authorities);
        });
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader(PRINCIPAL_REQUEST_HEADER);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }

}