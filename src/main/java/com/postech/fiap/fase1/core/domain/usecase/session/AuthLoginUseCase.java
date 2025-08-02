package com.postech.fiap.fase1.core.domain.usecase.session;

import com.postech.fiap.fase1.core.presenter.UserTokenDTOPresenter;
import com.postech.fiap.fase1.core.dto.auth.LoginRequestDTO;
import com.postech.fiap.fase1.core.dto.auth.UserTokenBodyDTO;
import com.postech.fiap.fase1.core.domain.usecase.user.UserReadUseCase;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.infrastructure.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthLoginUseCase {

    private static final Long TOKEN_EXPIRATION = DateUtils.MILLIS_PER_DAY;
    private static final String KEY_BODY = "body";

    @Value("${security.token-secret}")
    private String tokenSecret;
    private final UserReadUseCase userReadUseCase;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    private String getToken(UserDomain user) {
        Date expiration = new Date(System.currentTimeMillis() + TOKEN_EXPIRATION);
        return buildToken(UserTokenDTOPresenter.toUserTokenDTO(user, expiration));
    }

    private String buildToken(UserTokenBodyDTO userTokenBodyDTO) {
        return jwtUtils.buildJWTToken(KEY_BODY, tokenSecret, userTokenBodyDTO.getExpiration(), userTokenBodyDTO);
    }

    public String execute(LoginRequestDTO loginDTO) {
        try {
            UserDomain user = userReadUseCase.getUserByLogin(loginDTO.getLogin());
            if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                return getToken(user);
            }
        } catch (Exception e) {
            log.error("Error during login: {}", e.getMessage());
        }
        throw new ApplicationException("Error during login");
    }
}
