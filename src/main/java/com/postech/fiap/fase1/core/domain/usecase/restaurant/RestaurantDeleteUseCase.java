package com.postech.fiap.fase1.core.domain.usecase.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.gateway.restaurant.IRestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.validation.restaurant.RestaurantDeleteValidation;
import com.postech.fiap.fase1.core.validation.restaurant.implementation.RestaurantUserAllowedValidation;


public class RestaurantDeleteUseCase {

    private final RestaurantDeleteValidation restaurantDeleteValidation;
    private final IRestaurantGateway iRestaurantGateway;

    public RestaurantDeleteUseCase(IRestaurantGateway restaurantGateway, ISessionGateway sessionGateway) {
        this.iRestaurantGateway = restaurantGateway;
        this.restaurantDeleteValidation = new RestaurantUserAllowedValidation(sessionGateway);
    }

    public void execute(Long idRestaurant) {
        RestaurantDomain restaurantDomain = iRestaurantGateway.getOneById(idRestaurant);
        restaurantDeleteValidation.validate(restaurantDomain);
        iRestaurantGateway.deleteById(restaurantDomain.getId());
    }
}
