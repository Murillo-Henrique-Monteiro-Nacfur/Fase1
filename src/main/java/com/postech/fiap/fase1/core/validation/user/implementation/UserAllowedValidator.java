package com.postech.fiap.fase1.core.validation.user.implementation;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.validation.session.ISessionValidation;
import com.postech.fiap.fase1.core.validation.session.SessionUserAllowedValidator;
import com.postech.fiap.fase1.core.validation.user.UserUpdatePasswordValidation;
import com.postech.fiap.fase1.core.validation.user.UserUpdateValidation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAllowedValidator implements UserUpdateValidation, UserUpdatePasswordValidation {
    private final ISessionValidation sessionValidation;

    public UserAllowedValidator(ISessionGateway sessionGateway) {
        this.sessionValidation = new SessionUserAllowedValidator(sessionGateway);
    }

    public void validate(UserDomain userDomain) {
        sessionValidation.validate(userDomain.getId());
    }

    public void validate() {
        sessionValidation.validate();
    }
}
