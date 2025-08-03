package com.postech.fiap.fase1.core.domain.usecase.session;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.domain.usecase.user.UserGetByIdUseCase;
import com.postech.fiap.fase1.core.dto.auth.LoginRequestDTO;
import com.postech.fiap.fase1.core.dto.auth.UserTokenBodyDTO;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import com.postech.fiap.fase1.core.presenter.UserTokenDTOPresenter;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.infrastructure.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;


@Slf4j
public class AuthLoginUseCase {

    private static final Long TOKEN_EXPIRATION = DateUtils.MILLIS_PER_DAY;
    private static final String KEY_BODY = "body";

    private final String tokenSecret;
    private final UserGetByIdUseCase userGetByIdUseCase;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    public AuthLoginUseCase(String tokenSecret, IUserGateway iUserGateway) {
        this.tokenSecret = tokenSecret;
        this.userGetByIdUseCase = new UserGetByIdUseCase(iUserGateway);
        passwordEncoder = new BCryptPasswordEncoder();
        jwtUtils = new JWTUtils();
    }

    private String getToken(UserDomain user) {
        Date expiration = new Date(System.currentTimeMillis() + TOKEN_EXPIRATION);
        return buildToken(UserTokenDTOPresenter.toUserTokenDTO(user, expiration));
    }

    private String buildToken(UserTokenBodyDTO userTokenBodyDTO) {
        return jwtUtils.buildJWTToken(KEY_BODY, tokenSecret, userTokenBodyDTO.getExpiration(), userTokenBodyDTO);
    }

    public String execute(LoginRequestDTO loginDTO) {
        try {
            UserDomain user = userGetByIdUseCase.getUserByLogin(loginDTO.getLogin());
            if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                return getToken(user);
            }
        } catch (Exception e) {
            log.error("Error during login: {}", e.getMessage());
        }
        throw new ApplicationException("Error during login");
    }
}
