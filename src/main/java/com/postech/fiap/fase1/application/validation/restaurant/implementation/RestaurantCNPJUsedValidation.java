package com.postech.fiap.fase1.application.validation.restaurant.implementation;

import com.postech.fiap.fase1.application.validation.restaurant.RestaurantCreateValidation;
import com.postech.fiap.fase1.application.validation.restaurant.RestaurantUpdateValidation;
import com.postech.fiap.fase1.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.infrastructure.persistence.gateway.restaurant.RestaurantGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantCNPJUsedValidation implements RestaurantCreateValidation, RestaurantUpdateValidation {
    private final RestaurantGateway restaurantGateway;

    @Override
    public void validate(RestaurantDomain restaurantDomain) {
        if (restaurantGateway.hasRestaurantWithCNPJ(restaurantDomain.getId(), restaurantDomain.getCnpj())) {
            throw new ApplicationException("Already Has a restaurant with this CNPJ");
        }
    }
}
