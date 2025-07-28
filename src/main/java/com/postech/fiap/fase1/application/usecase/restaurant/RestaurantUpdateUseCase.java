package com.postech.fiap.fase1.application.usecase.restaurant;

import com.postech.fiap.fase1.application.validation.restaurant.RestaurantUpdateValidation;
import com.postech.fiap.fase1.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.infrastructure.persistence.gateway.restaurant.RestaurantGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantUpdateUseCase {

    private final RestaurantGateway restaurantGateway;
    private final List<RestaurantUpdateValidation> restaurantUpdateValidations;

    public RestaurantDomain execute(RestaurantDomain restaurantDomain) {
        validate(restaurantDomain);
        return restaurantGateway.createOrUpdate(restaurantDomain);
    }

    private void validate(RestaurantDomain restaurantDomain) {
        restaurantUpdateValidations
                .forEach(restaurantUpdateValidation ->
                        restaurantUpdateValidation.validate(restaurantDomain));
    }
}
