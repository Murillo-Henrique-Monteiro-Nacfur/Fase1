package com.postech.fiap.fase1.core.domain.validation.user.implementation;

import com.postech.fiap.fase1.core.domain.validation.user.UserCreateAdminValidation;
import com.postech.fiap.fase1.core.domain.validation.user.UserCreateValidation;
import com.postech.fiap.fase1.core.domain.validation.user.UserUpdatePasswordValidation;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserPasswordValidator implements UserCreateValidation, UserCreateAdminValidation, UserUpdatePasswordValidation {
    private static final String PASSWORD_AND_CONFIRMATION_DO_NOT_MATCH = "Password and confirmation do not match";
    public void validate(UserDomain userDomain) {
        if (userDomain.isPasswordDifferentFromConfirmation()) {
            log.warn(PASSWORD_AND_CONFIRMATION_DO_NOT_MATCH);
            throw new ApplicationException(PASSWORD_AND_CONFIRMATION_DO_NOT_MATCH);
        }
    }
}
