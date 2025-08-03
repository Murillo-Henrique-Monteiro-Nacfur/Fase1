package com.postech.fiap.fase1.core.domain.usecase.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.gateway.restaurant.IRestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.validation.restaurant.RestaurantUpdateValidation;
import com.postech.fiap.fase1.core.validation.restaurant.implementation.RestaurantCNPJUsedValidation;
import com.postech.fiap.fase1.core.validation.restaurant.implementation.RestaurantOpenCloseTimeValidation;
import com.postech.fiap.fase1.core.validation.restaurant.implementation.RestaurantUserAllowedValidation;

import java.util.List;

public class RestaurantUpdateUseCase {

    private final IRestaurantGateway iRestaurantGateway;
    private final List<RestaurantUpdateValidation> restaurantUpdateValidations;

    public RestaurantUpdateUseCase(IRestaurantGateway restaurantGateway, ISessionGateway sessionGateway) {
        iRestaurantGateway = restaurantGateway;
        restaurantUpdateValidations = List.of(new RestaurantCNPJUsedValidation(restaurantGateway), new RestaurantOpenCloseTimeValidation(), new RestaurantUserAllowedValidation(sessionGateway));
    }

    public RestaurantDomain execute(RestaurantDomain restaurantDomain) {
        var restaurantDomainOld = iRestaurantGateway.getOneById(restaurantDomain.getId());
        restaurantDomain.setUser(restaurantDomainOld.getUser());
        validate(restaurantDomain);
        return iRestaurantGateway.update(restaurantDomain);
    }

    private void validate(RestaurantDomain restaurantDomain) {
        restaurantUpdateValidations
                .forEach(restaurantUpdateValidation ->
                        restaurantUpdateValidation.validate(restaurantDomain));
    }
}
