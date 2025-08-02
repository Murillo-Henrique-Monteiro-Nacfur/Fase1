package com.postech.fiap.fase1.core.validation.user.implementation;

import com.postech.fiap.fase1.core.validation.user.UserCreateAdminValidation;
import com.postech.fiap.fase1.core.validation.user.UserCreateValidation;
import com.postech.fiap.fase1.core.validation.user.UserUpdateValidation;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
public class UserEmailAlreadyUsedValidator implements UserCreateAdminValidation, UserUpdateValidation, UserCreateValidation {
    private static final String USER_WITH_THIS_EMAIL_ALREADY_EXISTS = "User with this email already exists";
    private final UserGateway userGateway;

    public void validate(UserDomain userDomain) {
        if(userGateway.hasUserWithSameEmail(userDomain.getId(), userDomain.getEmail())) {
            log.warn(USER_WITH_THIS_EMAIL_ALREADY_EXISTS);
            throw new ApplicationException(USER_WITH_THIS_EMAIL_ALREADY_EXISTS);
        }
    }
}
