package com.postech.fiap.fase1.application.usecase.user;

import com.postech.fiap.fase1.application.validation.user.UserCreateAdminValidation;
import com.postech.fiap.fase1.application.validation.user.implementation.UserAllowedValidator;
import com.postech.fiap.fase1.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.domain.model.UserDomain;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.infrastructure.persistence.gateway.user.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCreateAdminUseCase {
private final UserAllowedValidator userAllowedValidator;
    private final UserGateway userGateway;
    private final List<UserCreateAdminValidation> userCreateAdminValidations;

    public UserDomain execute(UserDomain userDomain) {
        validate(userDomain);
        userDomain.setAdminRole();
        return userGateway.createOrUpdate(userDomain);
    }

    private void validate(UserDomain userDomain) {
        userCreateAdminValidations
                .forEach(restaurantCreationValidation ->
                        restaurantCreationValidation.validate(userDomain));
    }
}
