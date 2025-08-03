package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import com.postech.fiap.fase1.core.domain.validation.user.UserUpdateValidation;
import com.postech.fiap.fase1.core.domain.validation.user.implementation.UserAllowedValidator;
import com.postech.fiap.fase1.core.domain.validation.user.implementation.UserEmailAlreadyUsedValidator;
import com.postech.fiap.fase1.core.domain.validation.user.implementation.UserLoginAlreadyUsedValidator;

import java.util.List;

public class UserUpdateDetailsUseCase {
    private final IUserGateway iUserGateway;
    private final List<UserUpdateValidation> userUpdateValidations;

    public UserUpdateDetailsUseCase(IUserGateway iUserGateway, SessionGateway sessionGateway) {
        this.iUserGateway = iUserGateway;
        this.userUpdateValidations = List.of(new UserAllowedValidator(sessionGateway), new UserEmailAlreadyUsedValidator(iUserGateway), new UserLoginAlreadyUsedValidator(iUserGateway));
    }

    public UserDomain execute(UserDomain userDomain) {
        iUserGateway.getUserById(userDomain.getId());
        validate(userDomain);
        return iUserGateway.updateUser(userDomain);
    }

    private void validate(UserDomain userDomain) {
        userUpdateValidations
                .forEach(restaurantCreationValidation ->
                        restaurantCreationValidation.validate(userDomain));
    }
}
