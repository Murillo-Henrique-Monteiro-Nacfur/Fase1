package com.postech.fiap.fase1.core.validation.user.implementation;


import com.postech.fiap.fase1.core.validation.session.SessionValidation;
import com.postech.fiap.fase1.core.validation.user.UserUpdatePasswordValidation;
import com.postech.fiap.fase1.core.validation.user.UserUpdateValidation;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAllowedValidator implements UserUpdateValidation, UserUpdatePasswordValidation {
    private final SessionValidation sessionValidation;

    public void validate(UserDomain userDomain) {
        sessionValidation.validate(userDomain.getId());
    }
    public void validate() {
        sessionValidation.validate();
    }
}
