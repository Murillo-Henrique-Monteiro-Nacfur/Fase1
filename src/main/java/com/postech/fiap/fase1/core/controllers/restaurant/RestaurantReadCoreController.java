package com.postech.fiap.fase1.core.controllers.restaurant;

import com.postech.fiap.fase1.core.domain.usecase.restaurant.RestaurantReadUseCase;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.restaurant.RestaurantGateway;
import com.postech.fiap.fase1.core.presenter.RestaurantPresenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class RestaurantReadCoreController {

    private final RestaurantPresenter restaurantPresenter;
    private final RestaurantReadUseCase restaurantReadUseCase;

    public RestaurantReadCoreController(DataSource dataSource) {
        var restaurantGateway = RestaurantGateway.build(dataSource);
        this.restaurantReadUseCase = new RestaurantReadUseCase(restaurantGateway);
        this.restaurantPresenter = new RestaurantPresenter();
    }

    public RestaurantResponseDTO findById(Long idRestaurant) {
        var restaurantDomain = restaurantReadUseCase.getById(idRestaurant);
        return restaurantPresenter.toResponseDTO(restaurantDomain);
    }

    public Page<RestaurantResponseDTO> getAllPaged(Pageable pageable) {
        var restaurantDomains = restaurantReadUseCase.getAllPaged(pageable);

        return restaurantPresenter.toPageResponseDTOs(restaurantDomains);
    }
}