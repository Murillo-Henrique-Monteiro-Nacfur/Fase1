package com.postech.fiap.fase1.webapi.infrastructure.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityConfigurationUnitTest {

    @InjectMocks
    private SecurityConfiguration securityConfiguration;

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    @Test
    @DisplayName("Deve retornar uma instância de BCryptPasswordEncoder")
    void passwordEncoder_shouldReturnBCryptPasswordEncoder() {
        PasswordEncoder passwordEncoder = securityConfiguration.passwordEncoder();

        assertThat(passwordEncoder).isNotNull().isInstanceOf(BCryptPasswordEncoder.class);
    }

    @Test
    @DisplayName("Deve retornar o AuthenticationManager da configuração do Spring")
    void authenticationManager_shouldReturnManagerFromConfiguration() throws Exception {
        AuthenticationManager expectedManager = org.mockito.Mockito.mock(AuthenticationManager.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(expectedManager);

        AuthenticationManager actualManager = securityConfiguration.authenticationManager(authenticationConfiguration);

        assertThat(actualManager).isNotNull().isSameAs(expectedManager);
    }
}