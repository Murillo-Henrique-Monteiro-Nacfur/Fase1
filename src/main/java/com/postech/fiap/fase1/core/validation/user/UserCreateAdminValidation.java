package com.postech.fiap.fase1.core.validation.user;


import com.postech.fiap.fase1.core.domain.model.UserDomain;

public interface UserCreateAdminValidation {

    void validate(UserDomain userInputDTO);

}
