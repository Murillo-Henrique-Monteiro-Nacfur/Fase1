package com.postech.fiap.fase1.application.validation.restaurant.implementation;

import com.postech.fiap.fase1.application.validation.restaurant.RestaurantCreateValidation;
import com.postech.fiap.fase1.application.validation.restaurant.RestaurantDeleteValidation;
import com.postech.fiap.fase1.application.validation.restaurant.RestaurantUpdateValidation;
import com.postech.fiap.fase1.application.dto.auth.SessionDTO;
import com.postech.fiap.fase1.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.infrastructure.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantUserAllowedValidation implements RestaurantCreateValidation, RestaurantUpdateValidation, RestaurantDeleteValidation {
    private final SessionService sessionService;//todo provavelmente trocar pelo gateway

    @Override
    public void validate(RestaurantDomain restaurantDomain) {
        SessionDTO sessionDTO = sessionService.getSessionDTO();
        if (sessionDTO.isNotAdmin() || !restaurantDomain.getUser().getId().equals(sessionDTO.getUserId())) {
            throw new ApplicationException("Operation not allowed");
        }
    }
}
