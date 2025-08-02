package com.postech.fiap.fase1.core.domain.usecase.restaurant;

import com.postech.fiap.fase1.core.validation.restaurant.RestaurantUpdateValidation;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.gateway.restaurant.RestaurantGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantUpdateUseCase {

    private final RestaurantGateway restaurantGateway;
    private final List<RestaurantUpdateValidation> restaurantUpdateValidations;
    private final RestaurantReadUseCase restaurantReadUseCase;

    public RestaurantDomain execute(RestaurantDomain restaurantDomain) {
        var restaurantDomainOld = restaurantReadUseCase.getById(restaurantDomain.getId());
        restaurantDomain.setUser(restaurantDomainOld.getUser());
        validate(restaurantDomain);
        return restaurantGateway.update(restaurantDomain);
    }

    private void validate(RestaurantDomain restaurantDomain) {
        restaurantUpdateValidations
                .forEach(restaurantUpdateValidation ->
                        restaurantUpdateValidation.validate(restaurantDomain));
    }
}
