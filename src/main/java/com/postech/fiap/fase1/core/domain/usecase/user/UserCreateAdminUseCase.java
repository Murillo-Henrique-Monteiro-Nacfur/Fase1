package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import com.postech.fiap.fase1.core.validation.user.UserCreateAdminValidation;
import com.postech.fiap.fase1.core.validation.user.implementation.UserAdminAllowedValidator;
import com.postech.fiap.fase1.core.validation.user.implementation.UserEmailAlreadyUsedValidator;
import com.postech.fiap.fase1.core.validation.user.implementation.UserLoginAlreadyUsedValidator;
import com.postech.fiap.fase1.core.validation.user.implementation.UserPasswordValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserCreateAdminUseCase {
    private final UserGateway userGateway;
    private final List<UserCreateAdminValidation> userCreateAdminValidations;
    private final PasswordEncoder passwordEncoder;

    public UserCreateAdminUseCase(UserGateway userGateway, SessionGateway sessionGateway) {
        this.userGateway = userGateway;
        this.userCreateAdminValidations = List.of(new UserAdminAllowedValidator(sessionGateway), new UserEmailAlreadyUsedValidator(userGateway), new UserLoginAlreadyUsedValidator(userGateway), new UserPasswordValidator());
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserDomain execute(UserDomain userDomain) {
        validate(userDomain);
        userDomain.setAdminRole();
        userDomain.setPassword(passwordEncoder.encode(userDomain.getPassword()));
        return userGateway.createUser(userDomain);
    }

    private void validate(UserDomain userDomain) {
        userCreateAdminValidations
                .forEach(restaurantCreationValidation ->
                        restaurantCreationValidation.validate(userDomain));
    }
}
