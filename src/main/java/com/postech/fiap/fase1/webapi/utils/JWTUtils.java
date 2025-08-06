package com.postech.fiap.fase1.webapi.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {

    private JWTVerifier getJwtVerifier(String tokenSecret) {
        return JWT.require(Algorithm.HMAC256(tokenSecret)).build();
    }

    public String buildJWTToken(String keyBody, String tokenSecret, Date expiration, Object userTokenDTO) {
        try {
            return JWT
                    .create()
                    .withClaim(keyBody, JsonUtils.objectToJson(userTokenDTO))
                    .withExpiresAt(expiration)
                    .sign(Algorithm.HMAC256(tokenSecret));
        } catch (Exception e) {
            throw new ApplicationException("Error generating token.");
        }
    }

    public DecodedJWT verifyAndDecodeJWT(String token, String tokenSecret) {
        return getJwtVerifier(tokenSecret).verify(token);
    }
}
