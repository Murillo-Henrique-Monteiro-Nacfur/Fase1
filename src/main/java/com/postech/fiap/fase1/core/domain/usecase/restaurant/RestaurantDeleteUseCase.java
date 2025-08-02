package com.postech.fiap.fase1.core.domain.usecase.restaurant;

import com.postech.fiap.fase1.core.validation.restaurant.RestaurantDeleteValidation;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.gateway.restaurant.RestaurantGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantDeleteUseCase {

    private final RestaurantGateway restaurantGateway;
    private final RestaurantDeleteValidation restaurantDeleteValidation;
    private final RestaurantReadUseCase restaurantReadUseCase;

    public void execute(Long idRestaurant) {
        RestaurantDomain restaurantDomain = restaurantReadUseCase.getById(idRestaurant);
        restaurantDeleteValidation.validate(restaurantDomain);
        restaurantGateway.deleteById(restaurantDomain.getId());
    }
}
