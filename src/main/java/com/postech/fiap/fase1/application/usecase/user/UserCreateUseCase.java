package com.postech.fiap.fase1.application.usecase.user;

import com.postech.fiap.fase1.application.validation.user.UserCreateAdminValidation;
import com.postech.fiap.fase1.application.validation.user.UserCreateValidation;
import com.postech.fiap.fase1.application.validation.user.implementation.UserAllowedValidator;
import com.postech.fiap.fase1.domain.model.UserDomain;
import com.postech.fiap.fase1.infrastructure.persistence.gateway.user.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCreateUseCase {
private final UserAllowedValidator userAllowedValidator;
    private final UserGateway userGateway;
    private final List<UserCreateValidation> userCreateValidations;

    public UserDomain execute(UserDomain userDomain) {
        validate(userDomain);
        userDomain.setClientRole();
        return userGateway.createOrUpdate(userDomain);
    }

    private void validate(UserDomain userDomain) {
        userCreateValidations
                .forEach(restaurantCreationValidation ->
                        restaurantCreationValidation.validate(userDomain));
    }
}
