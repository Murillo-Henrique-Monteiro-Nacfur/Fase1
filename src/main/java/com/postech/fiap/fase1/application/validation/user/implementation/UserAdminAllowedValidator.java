package com.postech.fiap.fase1.application.validation.user.implementation;


import com.postech.fiap.fase1.application.validation.session.SessionValidation;
import com.postech.fiap.fase1.application.validation.user.UserCreateAdminValidation;
import com.postech.fiap.fase1.application.validation.user.UserUpdatePasswordValidation;
import com.postech.fiap.fase1.application.validation.user.UserUpdateValidation;
import com.postech.fiap.fase1.domain.model.UserDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAdminAllowedValidator implements UserUpdateValidation, UserUpdatePasswordValidation, UserCreateAdminValidation {
    private final SessionValidation sessionValidation;

    public void validate(UserDomain userDomain) {
        sessionValidation.validate();
    }

}
