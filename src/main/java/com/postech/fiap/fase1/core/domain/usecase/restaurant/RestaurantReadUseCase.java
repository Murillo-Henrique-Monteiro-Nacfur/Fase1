package com.postech.fiap.fase1.core.domain.usecase.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.gateway.restaurant.IRestaurantGateway;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class RestaurantReadUseCase {

    private final IRestaurantGateway iRestaurantGateway;

    public RestaurantReadUseCase(IRestaurantGateway restaurantGateway) {
        iRestaurantGateway = restaurantGateway;
    }

    public RestaurantDomain getById(Long idRestaurant) {
        return iRestaurantGateway.getById(idRestaurant)
                .orElseThrow(() -> new ApplicationException("Restaurant not found"));
    }

    public Page<RestaurantDomain> getAllPaged(Pageable pageable) {
        return iRestaurantGateway.getAllPaged(pageable);
    }
}
