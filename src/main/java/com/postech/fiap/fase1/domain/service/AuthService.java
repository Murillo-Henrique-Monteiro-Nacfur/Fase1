package com.postech.fiap.fase1.domain.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.postech.fiap.fase1.configuration.session.ThreadLocalStorage;
import com.postech.fiap.fase1.configuration.exception.ApplicationException;
import com.postech.fiap.fase1.domain.dto.auth.LoginRequestDTO;
import com.postech.fiap.fase1.domain.dto.auth.UserTokenDTO;
import com.postech.fiap.fase1.domain.model.User;
import com.postech.fiap.fase1.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Long TOKEN_EXPIRATION = DateUtils.MILLIS_PER_DAY;
    private static final String KEY_TOKEN = "token";
    private static final String KEY_BODY = "body";

    @Value("${security.token-secret}")
    private String tokenSecret;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public Map<String, String> checkAndRefreshToken(String token) {
        UserTokenDTO userTokenDTO = clarifyToken(token);
        userTokenDTO.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION));
        return Map.of(KEY_TOKEN, buildToken(userTokenDTO));
    }

    public String getToken(User user) {
        UserTokenDTO userTokenDTO = new UserTokenDTO(user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getRole().getDescription(),
                new Date(System.currentTimeMillis() + TOKEN_EXPIRATION)
        );
        return buildToken(userTokenDTO);
    }

    private String buildToken(UserTokenDTO userTokenDTO) {
        try {
            return JWT
                    .create()
                    .withClaim(KEY_BODY, JsonUtils.objectToJson(userTokenDTO))
                    .withExpiresAt(userTokenDTO.getExpiration())
                    .sign(Algorithm.HMAC256(tokenSecret));
        } catch (Exception e) {
            throw new ApplicationException("Error generating token.");
        }
    }

    public UserTokenDTO clarifyToken(String token) {
        try {
            DecodedJWT decodedJWT = getJwtVerifier().verify(token);
            return JsonUtils.jsonToObject(decodedJWT.getClaims().get(KEY_BODY).asString(), UserTokenDTO.class);
        } catch (Exception e) {
            throw new ApplicationException("Invalid Token", HttpStatus.BAD_REQUEST);
        }
    }

    private JWTVerifier getJwtVerifier() {
        return JWT.require(Algorithm.HMAC256(tokenSecret)).build();
    }

    public Map<String, String> login(LoginRequestDTO loginDTO) {
        User user = userService.getUserByLogin(loginDTO.getLogin());
        if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return Map.of(KEY_TOKEN, getToken(user));
        }
        throw new ApplicationException("Senha inválida");
    }

    public boolean checkAndLoadAccess(String token) {
        try {
            UserTokenDTO userTokenDTO = clarifyToken(token);
            ThreadLocalStorage.build(userTokenDTO, token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
