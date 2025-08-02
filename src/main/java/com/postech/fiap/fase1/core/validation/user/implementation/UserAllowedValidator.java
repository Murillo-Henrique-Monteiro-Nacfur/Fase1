package com.postech.fiap.fase1.core.validation.user.implementation;


import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.validation.session.SessionUserAllowedValidator;
import com.postech.fiap.fase1.core.validation.session.SessionValidation;
import com.postech.fiap.fase1.core.validation.user.UserUpdatePasswordValidation;
import com.postech.fiap.fase1.core.validation.user.UserUpdateValidation;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAllowedValidator implements UserUpdateValidation, UserUpdatePasswordValidation {
    private final SessionValidation sessionValidation;

    public UserAllowedValidator(SessionGateway sessionGateway) {
        this.sessionValidation = new SessionUserAllowedValidator(sessionGateway);
    }

    public void validate(UserDomain userDomain) {
        sessionValidation.validate(userDomain.getId());
    }
    public void validate() {
       sessionValidation.validate();
    }
}
