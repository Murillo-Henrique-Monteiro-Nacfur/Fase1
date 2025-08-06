package com.postech.fiap.fase1.core.controllers.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.usecase.restaurant.RestaurantCreateUseCase;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantRequestDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.gateway.restaurant.RestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import com.postech.fiap.fase1.core.presenter.RestaurantPresenter;

public class RestaurantCreateCoreController {

    private final RestaurantPresenter restaurantPresenter;
    private final RestaurantCreateUseCase restaurantCreateUseCase;

    public RestaurantCreateCoreController(DataSource dataSource, SessionSource sessionSource) {
        var restaurantGateway = RestaurantGateway.build(dataSource);
        var sessionGateway = SessionGateway.build(sessionSource);
        var userGateway = UserGateway.build(dataSource);

        this.restaurantCreateUseCase = new RestaurantCreateUseCase(restaurantGateway, userGateway, sessionGateway);
        this.restaurantPresenter = new RestaurantPresenter();
    }

    public RestaurantResponseDTO create(RestaurantRequestDTO restaurantRequestDTO) {
        RestaurantDomain restaurantDomain = restaurantCreateUseCase.execute(restaurantPresenter.toDomain(restaurantRequestDTO));
        return restaurantPresenter.toResponseDTO(restaurantDomain);
    }
}