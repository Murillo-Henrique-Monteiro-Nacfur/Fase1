package com.postech.fiap.fase1.core.domain.usecase.session;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.postech.fiap.fase1.core.dto.auth.UserTokenBodyDTO;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.infrastructure.session.ThreadLocalStorage;
import com.postech.fiap.fase1.infrastructure.utils.JWTUtils;
import com.postech.fiap.fase1.infrastructure.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthLoadAccessUseCase {

    private static final String KEY_BODY = "body";

    @Value("${security.token-secret}")
    private String tokenSecret;
    private final JWTUtils jwtUtils;

    public boolean execute(String token) {
        try {
            UserTokenBodyDTO userTokenBodyDTO = clarifyToken(token);
            ThreadLocalStorage.build(userTokenBodyDTO);
            return true;
        } catch (Exception e) {
            log.error("Error validating token: {}", e.getMessage());
            return false;
        }
    }

    private UserTokenBodyDTO clarifyToken(String token) {
        try {
            DecodedJWT decodedJWT = jwtUtils.verifyAndDecodeJWT(token, tokenSecret);
            return JsonUtils.jsonToObject(decodedJWT.getClaims().get(KEY_BODY).asString(), UserTokenBodyDTO.class);
        } catch (Exception e) {
            log.error("Error decoding token: {}", e.getMessage());
            throw new ApplicationException("Invalid Token", HttpStatus.BAD_REQUEST);
        }
    }
}
