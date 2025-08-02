package com.postech.fiap.fase1.core.validation.restaurant.implementation;

import com.postech.fiap.fase1.core.validation.restaurant.RestaurantCreateValidation;
import com.postech.fiap.fase1.core.validation.restaurant.RestaurantDeleteValidation;
import com.postech.fiap.fase1.core.validation.restaurant.RestaurantUpdateValidation;
import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.infrastructure.session.SessionDTOCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantUserAllowedValidation implements RestaurantCreateValidation, RestaurantUpdateValidation, RestaurantDeleteValidation {
    private final SessionDTOCreateUseCase sessionDTOCreateUseCase;

    @Override
    public void validate(RestaurantDomain restaurantDomain) {
        SessionDTO sessionDTO = sessionDTOCreateUseCase.getSessionDTO();
        if (sessionDTO.isNotAdmin() || !sessionDTO.getUserId().equals(restaurantDomain.getUser().getId())) {
            throw new ApplicationException("Operation not allowed");
        }
    }
}
