package com.postech.fiap.fase1.application.validation.user.implementation;


import com.postech.fiap.fase1.application.validation.session.SessionValidation;
import com.postech.fiap.fase1.application.validation.user.UserUpdatePasswordValidation;
import com.postech.fiap.fase1.application.validation.user.UserUpdateValidation;
import com.postech.fiap.fase1.domain.model.UserDomain;
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
