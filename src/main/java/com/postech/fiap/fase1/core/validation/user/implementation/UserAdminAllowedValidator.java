package com.postech.fiap.fase1.core.validation.user.implementation;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.validation.session.ISessionValidation;
import com.postech.fiap.fase1.core.validation.session.SessionUserAllowedValidator;
import com.postech.fiap.fase1.core.validation.user.UserCreateAdminValidation;
import com.postech.fiap.fase1.core.validation.user.UserUpdatePasswordValidation;

public class UserAdminAllowedValidator implements UserUpdatePasswordValidation, UserCreateAdminValidation {
    private final ISessionValidation sessionValidation;

    private UserAdminAllowedValidator(SessionUserAllowedValidator sessionUserAllowedValidator) {
        this.sessionValidation = sessionUserAllowedValidator;
    }

    public static UserAdminAllowedValidator build(ISessionGateway sessionGateway) {
        return new UserAdminAllowedValidator(new SessionUserAllowedValidator(sessionGateway));
    }

    public void validate(UserDomain userDomain) {
        sessionValidation.validate();
    }

}
