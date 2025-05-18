package com.postech.fiap.fase1.domain.service.session;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.postech.fiap.fase1.configuration.exception.ApplicationException;
import com.postech.fiap.fase1.configuration.session.ThreadLocalStorage;
import com.postech.fiap.fase1.domain.assembler.UserTokenDTOAssembler;
import com.postech.fiap.fase1.domain.dto.auth.LoginRequestDTO;
import com.postech.fiap.fase1.domain.dto.auth.UserTokenDTO;
import com.postech.fiap.fase1.domain.model.User;
import com.postech.fiap.fase1.domain.service.UserService;
import com.postech.fiap.fase1.utils.JWTUtils;
import com.postech.fiap.fase1.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private static final Long TOKEN_EXPIRATION = DateUtils.MILLIS_PER_DAY;
    private static final String KEY_TOKEN = "token";
    private static final String KEY_BODY = "body";

    @Value("${security.token-secret}")
    private String tokenSecret;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    private String getToken(User user) {
        Date expiration = new Date(System.currentTimeMillis() + TOKEN_EXPIRATION);
        return buildToken(UserTokenDTOAssembler.toUserTokenDTO(user, expiration));
    }

    private String buildToken(UserTokenDTO userTokenDTO) {
        return jwtUtils.buildToken(KEY_BODY, tokenSecret, userTokenDTO.getExpiration(), userTokenDTO);
    }

    private UserTokenDTO clarifyToken(String token) {
        try {
            DecodedJWT decodedJWT = jwtUtils.verifyAndDecodeJWT(token, tokenSecret);
            return JsonUtils.jsonToObject(decodedJWT.getClaims().get(KEY_BODY).asString(), UserTokenDTO.class);
        } catch (Exception e) {
            log.error("Error decoding token: {}", e.getMessage());
            throw new ApplicationException("Invalid Token", HttpStatus.BAD_REQUEST);
        }
    }

    public Map<String, String> login(LoginRequestDTO loginDTO) {
        User user = userService.getUserByLogin(loginDTO.getLogin());
        if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return Map.of(KEY_TOKEN, getToken(user));
        }
        throw new ApplicationException("Error validating login", HttpStatus.UNAUTHORIZED);
    }

    public boolean checkAndLoadAccess(String token) {
        try {
            UserTokenDTO userTokenDTO = clarifyToken(token);
            ThreadLocalStorage.build(userTokenDTO);
            return true;
        } catch (Exception e) {
            log.error("Error validating token: {}", e.getMessage());
            return false;
        }
    }
}
