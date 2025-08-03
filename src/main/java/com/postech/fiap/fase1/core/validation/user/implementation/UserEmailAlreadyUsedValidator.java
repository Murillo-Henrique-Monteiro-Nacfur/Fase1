package com.postech.fiap.fase1.core.validation.user.implementation;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import com.postech.fiap.fase1.core.validation.user.UserCreateAdminValidation;
import com.postech.fiap.fase1.core.validation.user.UserCreateValidation;
import com.postech.fiap.fase1.core.validation.user.UserUpdateValidation;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserEmailAlreadyUsedValidator implements UserCreateAdminValidation, UserUpdateValidation, UserCreateValidation {
    private static final String USER_WITH_THIS_EMAIL_ALREADY_EXISTS = "User with this email already exists";
    private final IUserGateway iUserGateway;

    public void validate(UserDomain userDomain) {
        if (iUserGateway.hasUserWithSameEmail(userDomain.getId(), userDomain.getEmail())) {
            log.warn(USER_WITH_THIS_EMAIL_ALREADY_EXISTS);
            throw new ApplicationException(USER_WITH_THIS_EMAIL_ALREADY_EXISTS);
        }
    }
}
