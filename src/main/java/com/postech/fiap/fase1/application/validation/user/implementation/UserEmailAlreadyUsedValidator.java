package com.postech.fiap.fase1.application.validation.user.implementation;

import com.postech.fiap.fase1.application.validation.user.UserCreateAdminValidation;
import com.postech.fiap.fase1.application.validation.user.UserCreateValidation;
import com.postech.fiap.fase1.application.validation.user.UserUpdateValidation;
import com.postech.fiap.fase1.domain.model.UserDomain;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.infrastructure.persistence.gateway.user.UserGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
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
