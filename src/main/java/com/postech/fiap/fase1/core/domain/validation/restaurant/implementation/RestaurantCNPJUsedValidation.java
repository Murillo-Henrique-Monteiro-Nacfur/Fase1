package com.postech.fiap.fase1.core.domain.validation.restaurant.implementation;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.gateway.restaurant.IRestaurantGateway;
import com.postech.fiap.fase1.core.domain.validation.restaurant.RestaurantCreateValidation;
import com.postech.fiap.fase1.core.domain.validation.restaurant.RestaurantUpdateValidation;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;

public class RestaurantCNPJUsedValidation implements RestaurantCreateValidation, RestaurantUpdateValidation {
    private final IRestaurantGateway iRestaurantGateway;

    public RestaurantCNPJUsedValidation(IRestaurantGateway restaurantGateway) {
        iRestaurantGateway = restaurantGateway;
    }

    @Override
    public void validate(RestaurantDomain restaurantDomain) {
        if (iRestaurantGateway.hasRestaurantWithCNPJ(restaurantDomain.getId(), restaurantDomain.getCnpj())) {
            throw new ApplicationException("Already Has a restaurant with this CNPJ");
        }
    }
}
