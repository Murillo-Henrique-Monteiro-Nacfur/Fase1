package com.postech.fiap.fase1.application.usecase.restaurant;

import com.postech.fiap.fase1.application.validation.restaurant.RestaurantDeleteValidation;
import com.postech.fiap.fase1.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.infrastructure.persistence.gateway.restaurant.RestaurantGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantDeleteUseCase {

    private final RestaurantGateway restaurantGateway;
    private final RestaurantDeleteValidation restaurantCreateValidation;
    private final RestaurantReadUseCase restaurantReadUseCase;

    public void execute(Long idRestaurant) {
        RestaurantDomain restaurantDomain = restaurantReadUseCase.getById(idRestaurant);
        restaurantCreateValidation.validate(restaurantDomain);
        restaurantGateway.deleteById(restaurantDomain.getId());
    }
}
