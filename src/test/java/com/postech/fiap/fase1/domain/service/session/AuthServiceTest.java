package com.postech.fiap.fase1.domain.service.session;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.postech.fiap.fase1.configuration.exception.ApplicationException;
import com.postech.fiap.fase1.domain.dto.auth.LoginRequestDTO;
import com.postech.fiap.fase1.domain.dto.auth.UserTokenDTO;
import com.postech.fiap.fase1.domain.model.User;
import com.postech.fiap.fase1.domain.service.UserService;
import com.postech.fiap.fase1.environment.EnvUser;
import com.postech.fiap.fase1.utils.JWTUtils;
import com.postech.fiap.fase1.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"security.token-secret=tokenSecret"})
class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldDoLoginReturnsTokenForValidCredentials() {
        LoginRequestDTO loginDTO = new LoginRequestDTO("userLogin", "userPassword");
        User user = EnvUser.getUserClient();

        when(userService.getUserByLogin(any())).thenReturn(user);
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(jwtUtils.buildToken(any(), any(), any(), any())).thenReturn("generatedToken");

        Map<String, String> result = authService.login(loginDTO);

        assertThat(result).isNotNull().containsEntry("token", "generatedToken");
    }

    @Test
    void shouldDoLoginThrowsExceptionForInvalidPassword() {
        LoginRequestDTO loginDTO = new LoginRequestDTO("userLogin", "wrongPassword");
        User user = new User();
        user.setPassword("encodedPassword");

        when(userService.getUserByLogin(loginDTO.getLogin())).thenReturn(user);
        when(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())).thenReturn(false);

        assertThatThrownBy(() -> authService.login(loginDTO))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("Error validating login");
    }

    @Test
    void shouldAllowAccessCheckAndLoadAccessReturnsTrueForValidToken() {
        String token = "validToken";
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        var decodedJWT = mock(DecodedJWT.class);
        var claim = mock(Claim.class);
        when(claim.asString()).thenReturn("{}");
        when(decodedJWT.getClaims()).thenReturn(Map.of("body", claim));
        when(jwtUtils.verifyAndDecodeJWT(any(), any())).thenReturn(decodedJWT);
        try (MockedStatic<JsonUtils> utilities = Mockito.mockStatic(JsonUtils.class)) {
            utilities.when(() -> JsonUtils.jsonToObject(any(), any())).thenReturn(userTokenDTO);
        }

        boolean result = authService.checkAndLoadAccess(token);

        assertThat(result).isTrue();
    }

    @Test
    void shouldDenyAccessInvalidtoken() {
        String token = "validToken";
        when(jwtUtils.verifyAndDecodeJWT(any(), any())).thenThrow(new ApplicationException(""));

        boolean result = authService.checkAndLoadAccess(token);

        assertThat(result).isFalse();
    }
//
//    @Test
//    void checkAndLoadAccessReturnsFalseForInvalidToken() {
//        String token = "invalidToken";
//
//        when(jwtUtils.verifyAndDecodeJWT(token, authService.getTokenSecret())).thenThrow(JWTVerificationException.class);
//
//        boolean result = authService.checkAndLoadAccess(token);
//
//        assertThat(result).isFalse();
//    }
//
//    @Test
//    void clarifyTokenThrowsExceptionForInvalidToken() {
//        String token = "invalidToken";
//
//        when(jwtUtils.verifyAndDecodeJWT(token, authService.getTokenSecret())).thenThrow(JWTVerificationException.class);
//
//        assertThatThrownBy(() -> authService.clarifyToken(token))
//                .isInstanceOf(ApplicationException.class)
//                .hasMessage("Invalid Token")
//                .extracting("status").isEqualTo(HttpStatus.BAD_REQUEST);
//    }
}