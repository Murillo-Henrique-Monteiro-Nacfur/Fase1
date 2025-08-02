package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.presenter.UserPresenter;
import com.postech.fiap.fase1.core.validation.user.UserUpdateValidation;
import com.postech.fiap.fase1.core.validation.user.implementation.UserAllowedValidator;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserUpdateDetailsUseCase {
private final UserAllowedValidator userAllowedValidator;
    private final UserGateway userGateway;
    private final UserReadUseCase userReadUseCase;
    private final List<UserUpdateValidation> userUpdateValidations;
    private final UserPresenter userPresenter;
    public UserDomain execute(UserDomain userDomain) {
        userReadUseCase.getById(userDomain.getId());

        validate(userDomain);
        return userGateway.update(userDomain);
    }

    private void validate(UserDomain userDomain) {
        userUpdateValidations
                .forEach(restaurantCreationValidation ->
                        restaurantCreationValidation.validate(userDomain));
    }
}
