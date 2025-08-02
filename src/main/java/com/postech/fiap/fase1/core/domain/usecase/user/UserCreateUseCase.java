package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.validation.user.UserCreateValidation;
import com.postech.fiap.fase1.core.validation.user.implementation.UserAllowedValidator;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCreateUseCase {
private final UserAllowedValidator userAllowedValidator;
    private final UserGateway userGateway;
    private final List<UserCreateValidation> userCreateValidations;
    private final PasswordEncoder passwordEncoder;

    public UserDomain execute(UserDomain userDomain) {
        validate(userDomain);
        userDomain.setClientRole();
        userDomain.setPassword(passwordEncoder.encode(userDomain.getPassword()));
        return userGateway.create(userDomain);
    }

    private void validate(UserDomain userDomain) {
        userCreateValidations
                .forEach(restaurantCreationValidation ->
                        restaurantCreationValidation.validate(userDomain));
    }
}
