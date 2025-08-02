package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import com.postech.fiap.fase1.core.validation.user.UserCreateValidation;
import com.postech.fiap.fase1.core.validation.user.implementation.UserEmailAlreadyUsedValidator;
import com.postech.fiap.fase1.core.validation.user.implementation.UserLoginAlreadyUsedValidator;
import com.postech.fiap.fase1.core.validation.user.implementation.UserPasswordValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserCreateUseCase {
    private final UserGateway userGateway;
    private final List<UserCreateValidation> userCreateValidations;
    private final PasswordEncoder passwordEncoder;

    public UserCreateUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
        this.userCreateValidations = List.of(new UserEmailAlreadyUsedValidator(userGateway), new UserLoginAlreadyUsedValidator(userGateway), new UserPasswordValidator());
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserDomain execute(UserDomain userDomain) {
        validate(userDomain);
        userDomain.setClientRole();
        userDomain.setPassword(passwordEncoder.encode(userDomain.getPassword()));
        return userGateway.createUser(userDomain);
    }

    private void validate(UserDomain userDomain) {
        userCreateValidations
                .forEach(restaurantCreationValidation ->
                        restaurantCreationValidation.validate(userDomain));
    }
}
