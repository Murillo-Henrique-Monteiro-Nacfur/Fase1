package com.postech.fiap.fase1.core.domain.usecase.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.restaurant.IRestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import com.postech.fiap.fase1.core.validation.restaurant.RestaurantCreateValidation;
import com.postech.fiap.fase1.core.validation.restaurant.implementation.RestaurantCNPJUsedValidation;
import com.postech.fiap.fase1.core.validation.restaurant.implementation.RestaurantOpenCloseTimeValidation;
import com.postech.fiap.fase1.core.validation.restaurant.implementation.RestaurantUserAllowedValidation;

import java.util.List;

public class RestaurantCreateUseCase {

    private final IRestaurantGateway iRestaurantGateway;
    private final List<RestaurantCreateValidation> restaurantCreateValidations;
    private final IUserGateway iUserGateway;

    public RestaurantCreateUseCase(IRestaurantGateway restaurantGateway, IUserGateway userGateway, ISessionGateway sessionGateway) {
        this.iRestaurantGateway = restaurantGateway;
        this.iUserGateway = userGateway;
        restaurantCreateValidations = List.of(new RestaurantCNPJUsedValidation(restaurantGateway), new RestaurantOpenCloseTimeValidation(), new RestaurantUserAllowedValidation(sessionGateway));
    }

    public RestaurantDomain execute(RestaurantDomain restaurantDomain) {
        restaurantDomain.setId(null);
        UserDomain userDomain = iUserGateway.getUserById(restaurantDomain.getIdUserOwner());
        restaurantDomain.setUser(userDomain);
        validate(restaurantDomain);
        return iRestaurantGateway.create(restaurantDomain);
    }

    private void validate(RestaurantDomain restaurantDomain) {
        restaurantCreateValidations
                .forEach(restaurantCreationValidation ->
                        restaurantCreationValidation.validate(restaurantDomain));
    }
}
