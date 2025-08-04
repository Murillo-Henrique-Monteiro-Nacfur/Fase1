package com.postech.fiap.fase1.core.domain.validation.restaurant.implementation;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.domain.validation.restaurant.RestaurantCreateValidation;
import com.postech.fiap.fase1.core.domain.validation.restaurant.RestaurantDeleteValidation;
import com.postech.fiap.fase1.core.domain.validation.restaurant.RestaurantUpdateValidation;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;


public class RestaurantUserAllowedValidation implements RestaurantCreateValidation, RestaurantUpdateValidation, RestaurantDeleteValidation {
    private final ISessionGateway sessionGateway;

    public RestaurantUserAllowedValidation(ISessionGateway sessionGateway) {
        this.sessionGateway = sessionGateway;
    }

    @Override
    public void validate(RestaurantDomain restaurantDomain) {
        SessionDTO sessionDTO = sessionGateway.getSessionDTO();
        if (sessionDTO.isNotAdmin() && !sessionDTO.getUserId().equals(restaurantDomain.getUser().getId())) {
            throw new ApplicationException("Operation not allowed");
        }
    }
}