package com.postech.fiap.fase1.core.domain.usecase.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.core.gateway.restaurant.RestaurantGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantReadUseCase {

    private final RestaurantGateway restaurantGateway;

    public RestaurantDomain getById(Long idRestaurant) {
        return restaurantGateway.getById(idRestaurant)
                .orElseThrow(() -> new ApplicationException("Restaurant not found"));
    }

    public Page<RestaurantDomain> getAllPaged(Pageable pageable) {
        return restaurantGateway.getAllPaged(pageable);
    }
}
