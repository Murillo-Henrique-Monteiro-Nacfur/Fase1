package com.postech.fiap.fase1.core.domain.usecase.restaurant;

import com.postech.fiap.fase1.core.validation.restaurant.RestaurantCreateValidation;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.gateway.restaurant.RestaurantGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantCreateUseCase {

    private final RestaurantGateway restaurantGateway;
    private final List<RestaurantCreateValidation> restaurantCreateValidations;

    public RestaurantDomain execute(RestaurantDomain restaurantDomain) {
        restaurantDomain.setId(null);
        validate(restaurantDomain);
        return restaurantGateway.create(restaurantDomain);
    }

    private void validate(RestaurantDomain restaurantDomain) {
        restaurantCreateValidations
                .forEach(restaurantCreationValidation ->
                        restaurantCreationValidation.validate(restaurantDomain));
    }
}
