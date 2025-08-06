package com.postech.fiap.fase1.core.controllers.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.usecase.restaurant.RestaurantUpdateUseCase;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantRequestUpdateDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.gateway.restaurant.RestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.presenter.RestaurantPresenter;

public class RestaurantUpdateCoreController {

    private final RestaurantPresenter restaurantPresenter;
    private final RestaurantUpdateUseCase restaurantUpdateUseCase;

    public RestaurantUpdateCoreController(DataSource dataSource, SessionSource sessionSource) {
        var restaurantGateway = RestaurantGateway.build(dataSource);
        var sessionGateway = SessionGateway.build(sessionSource);

        this.restaurantUpdateUseCase = new RestaurantUpdateUseCase(restaurantGateway, sessionGateway);
        this.restaurantPresenter = new RestaurantPresenter();
    }

    public RestaurantResponseDTO update(RestaurantRequestUpdateDTO restaurantRequestUpdateDTO) {
        RestaurantDomain restaurantDomain = restaurantUpdateUseCase.execute(restaurantPresenter.toDomain(restaurantRequestUpdateDTO));
        return restaurantPresenter.toResponseDTO(restaurantDomain);
    }
}