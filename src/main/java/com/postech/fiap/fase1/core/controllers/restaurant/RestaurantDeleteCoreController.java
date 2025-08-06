package com.postech.fiap.fase1.core.controllers.restaurant;

import com.postech.fiap.fase1.core.domain.usecase.restaurant.RestaurantDeleteUseCase;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.gateway.restaurant.RestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;

public class RestaurantDeleteCoreController {

    private final RestaurantDeleteUseCase restaurantDeleteUseCase;

    public RestaurantDeleteCoreController(DataSource dataSource, SessionSource sessionSource) {
        var restaurantGateway = RestaurantGateway.build(dataSource);
        var sessionGateway = SessionGateway.build(sessionSource);

        this.restaurantDeleteUseCase = new RestaurantDeleteUseCase(restaurantGateway, sessionGateway);
    }

    public void delete(Long idRestaurant) {
        restaurantDeleteUseCase.execute(idRestaurant);
    }

}
