package com.postech.fiap.fase1.webapi.infrastructure.security;

import com.postech.fiap.fase1.webapi.data.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RoleHierarchyConfigTest {

    private RoleHierarchy roleHierarchy;

    @BeforeEach
    void setUp() {
        roleHierarchy = RoleHierarchyConfig.roleHierarchy();
    }

    @Test
    @DisplayName("ADMIN deve ter acesso aos perfis de ADMIN, CLIENT e VIEWER")
    void roleHierarchy_adminShouldReachAdminClientAndViewer() {
        GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_" + Role.ADMIN.name());

        Collection<? extends GrantedAuthority> reachableAuthorities = roleHierarchy.getReachableGrantedAuthorities(List.of(adminAuthority));

        assertThat(reachableAuthorities)
                .isNotNull()
                .hasSize(3);
    }

    @Test
    @DisplayName("CLIENT deve ter acesso aos perfis de CLIENT e VIEWER, mas não ADMIN")
    void roleHierarchy_clientShouldReachClientAndViewer() {
        GrantedAuthority clientAuthority = new SimpleGrantedAuthority("ROLE_" + Role.CLIENT.name());

        Collection<? extends GrantedAuthority> reachableAuthorities = roleHierarchy.getReachableGrantedAuthorities(List.of(clientAuthority));

        assertThat(reachableAuthorities)
                .isNotNull()
                .hasSize(2);
    }

    @Test
    @DisplayName("VIEWER deve ter acesso apenas ao perfil de VIEWER")
    void roleHierarchy_viewerShouldOnlyReachViewer() {
        GrantedAuthority viewerAuthority = new SimpleGrantedAuthority("ROLE_" + Role.VIEWER.name());

        Collection<? extends GrantedAuthority> reachableAuthorities = roleHierarchy.getReachableGrantedAuthorities(List.of(viewerAuthority));

        assertThat(reachableAuthorities)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    @DisplayName("Deve criar um MethodSecurityExpressionHandler com a hierarquia de perfis definida")
    void methodSecurityExpressionHandler_shouldContainTheRoleHierarchy() {
        MethodSecurityExpressionHandler expressionHandler = RoleHierarchyConfig.methodSecurityExpressionHandler(roleHierarchy);

        assertThat(expressionHandler).isNotNull().isInstanceOf(DefaultMethodSecurityExpressionHandler.class);
    }
}