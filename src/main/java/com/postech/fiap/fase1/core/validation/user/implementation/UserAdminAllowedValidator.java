package com.postech.fiap.fase1.core.validation.user.implementation;


import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.validation.session.SessionUserAllowedValidator;
import com.postech.fiap.fase1.core.validation.session.SessionValidation;
import com.postech.fiap.fase1.core.validation.user.UserCreateAdminValidation;
import com.postech.fiap.fase1.core.validation.user.UserUpdatePasswordValidation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAdminAllowedValidator implements UserUpdatePasswordValidation, UserCreateAdminValidation {
    private final SessionValidation sessionValidation;

    public UserAdminAllowedValidator(SessionGateway sessionGateway) {
        this.sessionValidation = new SessionUserAllowedValidator(sessionGateway);
    }

    public void validate(UserDomain userDomain) {
        sessionValidation.validate();
    }

}
