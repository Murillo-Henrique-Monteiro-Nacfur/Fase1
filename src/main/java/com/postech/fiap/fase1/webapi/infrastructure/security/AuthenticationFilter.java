package com.postech.fiap.fase1.webapi.infrastructure.security;

import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.webapi.infrastructure.session.AuthLoadAccessService;
import com.postech.fiap.fase1.webapi.infrastructure.session.ThreadLocalStorage;
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
    private final AuthLoadAccessService authLoadAccessService;

    @Lazy
    public AuthenticationFilter(AuthLoadAccessService authLoadAccessService) {
        this.authLoadAccessService = authLoadAccessService;
        setAuthenticationManager(createAuthenticationManager());
    }

    private AuthenticationManager createAuthenticationManager() {
        return (authentication -> {
            String token = (String) authentication.getPrincipal();
            if (token == null || token.isEmpty() || !authLoadAccessService.execute(token)) {
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