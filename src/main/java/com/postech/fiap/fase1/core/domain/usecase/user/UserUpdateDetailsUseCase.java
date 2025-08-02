package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import com.postech.fiap.fase1.core.validation.user.UserUpdateValidation;
import com.postech.fiap.fase1.core.validation.user.implementation.UserAllowedValidator;
import com.postech.fiap.fase1.core.validation.user.implementation.UserEmailAlreadyUsedValidator;
import com.postech.fiap.fase1.core.validation.user.implementation.UserLoginAlreadyUsedValidator;

import java.util.List;

public class UserUpdateDetailsUseCase {
    private final UserGateway userGateway;
    private final List<UserUpdateValidation> userUpdateValidations;

    public UserUpdateDetailsUseCase(UserGateway userGateway, SessionGateway sessionGateway) {
        this.userGateway = userGateway;
        this.userUpdateValidations = List.of(new UserAllowedValidator(sessionGateway), new UserEmailAlreadyUsedValidator(userGateway), new UserLoginAlreadyUsedValidator(userGateway));
    }

    public UserDomain execute(UserDomain userDomain) {
        userGateway.getUserById(userDomain.getId());
        validate(userDomain);
        return userGateway.updateUser(userDomain);
    }

    private void validate(UserDomain userDomain) {
        userUpdateValidations
                .forEach(restaurantCreationValidation ->
                        restaurantCreationValidation.validate(userDomain));
    }
}
