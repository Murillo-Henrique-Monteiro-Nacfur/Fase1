package com.postech.fiap.fase1.infrastructure.security;

import com.postech.fiap.fase1.infrastructure.session.ThreadLocalStorage;
import com.postech.fiap.fase1.application.dto.auth.SessionDTO;
import com.postech.fiap.fase1.domain.service.session.AuthService;
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
public class AuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final String PRINCIPAL_REQUEST_HEADER = "X-Auth-Token";
    private final AuthService authService;

    @Lazy
    public AuthenticationFilter(AuthService authService) {
        this.authService = authService;
        setAuthenticationManager(createAuthenticationManager());
    }

    private AuthenticationManager createAuthenticationManager() {
        return (authentication -> {
            String token = (String) authentication.getPrincipal();
            if (token == null || token.isEmpty() || !authService.checkAndLoadAccess(token)) {
                return new UsernamePasswordAuthenticationToken(null, null);
            }
            SessionDTO sessionDTO = ThreadLocalStorage.getSession();
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(RoleHierarchyConfig.ROLE_PREFIX + sessionDTO.getUserRole()));
            return new UsernamePasswordAuthenticationToken(sessionDTO.getUserLogin(), null, authorities);
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