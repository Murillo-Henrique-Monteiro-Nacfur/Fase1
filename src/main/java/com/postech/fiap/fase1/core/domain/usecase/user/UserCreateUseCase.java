package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import com.postech.fiap.fase1.core.domain.validation.user.UserCreateValidation;
import com.postech.fiap.fase1.core.domain.validation.user.implementation.UserEmailAlreadyUsedValidator;
import com.postech.fiap.fase1.core.domain.validation.user.implementation.UserLoginAlreadyUsedValidator;
import com.postech.fiap.fase1.core.domain.validation.user.implementation.UserPasswordValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserCreateUseCase {
    private final IUserGateway iUserGateway;
    private final List<UserCreateValidation> userCreateValidations;
    private final PasswordEncoder passwordEncoder;

    public UserCreateUseCase(IUserGateway iUserGateway) {
        this.iUserGateway = iUserGateway;
        this.userCreateValidations = List.of(new UserEmailAlreadyUsedValidator(iUserGateway), new UserLoginAlreadyUsedValidator(iUserGateway), new UserPasswordValidator());
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserDomain execute(UserDomain userDomain) {
        validate(userDomain);
        userDomain.setClientRole();
        userDomain.setPassword(passwordEncoder.encode(userDomain.getPassword()));
        return iUserGateway.createUser(userDomain);
    }

    private void validate(UserDomain userDomain) {
        userCreateValidations
                .forEach(restaurantCreationValidation ->
                        restaurantCreationValidation.validate(userDomain));
    }
}
