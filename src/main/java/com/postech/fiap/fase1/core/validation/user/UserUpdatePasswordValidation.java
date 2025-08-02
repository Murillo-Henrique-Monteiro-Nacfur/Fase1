package com.postech.fiap.fase1.core.validation.user;


import com.postech.fiap.fase1.core.domain.model.UserDomain;

public interface UserUpdatePasswordValidation {

    void validate(UserDomain userDomain);

}
