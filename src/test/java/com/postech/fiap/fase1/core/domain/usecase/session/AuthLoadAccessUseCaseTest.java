package com.postech.fiap.fase1.core.domain.usecase.session;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.postech.fiap.fase1.core.dto.auth.UserTokenBodyDTO;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.webapi.utils.JWTUtils;
import com.postech.fiap.fase1.webapi.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthLoadAccessUseCaseTest {

    @Mock
    private JWTUtils jwtUtils;
    @Mock
    private DecodedJWT decodedJWT;
    @InjectMocks
    private AuthLoadAccessUseCase authLoadAccessUseCase;

    @BeforeEach
    void setUp() {
        authLoadAccessUseCase = new AuthLoadAccessUseCase(jwtUtils);
        try {
            java.lang.reflect.Field field = AuthLoadAccessUseCase.class.getDeclaredField("tokenSecret");
            field.setAccessible(true);
            field.set(authLoadAccessUseCase, "secret");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Deve retornar true e popular ThreadLocalStorage quando o token for válido")
    void execute_whenTokenIsValid_shouldReturnTrue() {
        String token = "valid.token";
        UserTokenBodyDTO userTokenBodyDTO = new UserTokenBodyDTO();
        Map<String, com.auth0.jwt.interfaces.Claim> claims = new HashMap<>();
        com.auth0.jwt.interfaces.Claim claim = mock(com.auth0.jwt.interfaces.Claim.class);
        claims.put("body", claim);
        when(jwtUtils.verifyAndDecodeJWT(token, "secret")).thenReturn(decodedJWT);
        when(decodedJWT.getClaims()).thenReturn(claims);
        when(claim.asString()).thenReturn("{\"id\":1}");
        Mockito.mockStatic(JsonUtils.class).when(() -> JsonUtils.jsonToObject("{\"id\":1}", UserTokenBodyDTO.class)).thenReturn(userTokenBodyDTO);

        boolean result = authLoadAccessUseCase.execute(token);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando o token for inválido")
    void execute_whenTokenIsInvalid_shouldReturnFalse() {
        String token = "invalid.token";
        when(jwtUtils.verifyAndDecodeJWT(token, "secret")).thenThrow(new ApplicationException("Invalid Token", HttpStatus.BAD_REQUEST));

        boolean result = authLoadAccessUseCase.execute(token);
        assertThat(result).isFalse();
    }
}