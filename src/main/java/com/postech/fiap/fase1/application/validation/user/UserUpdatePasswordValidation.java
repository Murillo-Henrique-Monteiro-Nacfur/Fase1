package com.postech.fiap.fase1.application.validation.user;


import com.postech.fiap.fase1.domain.model.UserDomain;

public interface UserUpdatePasswordValidation {

    void validate(UserDomain userDomain);

}
