package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import com.postech.fiap.fase1.core.domain.validation.user.UserUpdatePasswordValidation;
import com.postech.fiap.fase1.core.domain.validation.user.implementation.UserAdminAllowedValidator;
import com.postech.fiap.fase1.core.domain.validation.user.implementation.UserAllowedValidator;
import com.postech.fiap.fase1.core.domain.validation.user.implementation.UserPasswordValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserUpdatePasswordUseCase {

    private final List<UserUpdatePasswordValidation> userUpdatePasswordValidations;
    private final PasswordEncoder passwordEncoder;
    private final IUserGateway iUserGateway;

    public UserUpdatePasswordUseCase(IUserGateway iUserGateway, SessionGateway sessionGateway) {
        this.iUserGateway = iUserGateway;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userUpdatePasswordValidations = List.of(UserAdminAllowedValidator.build(sessionGateway), new UserAllowedValidator(sessionGateway), new UserPasswordValidator());
    }

    public UserDomain execute(UserDomain userDomain) {
        iUserGateway.getUserById(userDomain.getId());
        validate(userDomain);
        userDomain.setPassword(passwordEncoder.encode(userDomain.getPassword()));
        return iUserGateway.updateUserPassoword(userDomain);
    }

    private void validate(UserDomain userDomain) {
        userUpdatePasswordValidations
                .forEach(restaurantCreationValidation ->
                        restaurantCreationValidation.validate(userDomain));
    }
}