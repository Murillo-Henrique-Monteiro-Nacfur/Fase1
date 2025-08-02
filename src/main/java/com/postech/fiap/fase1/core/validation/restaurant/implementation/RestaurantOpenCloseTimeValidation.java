package com.postech.fiap.fase1.core.validation.restaurant.implementation;

import com.postech.fiap.fase1.core.validation.restaurant.RestaurantCreateValidation;
import com.postech.fiap.fase1.core.validation.restaurant.RestaurantUpdateValidation;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import org.springframework.stereotype.Component;

@Component
public class RestaurantOpenCloseTimeValidation implements RestaurantCreateValidation, RestaurantUpdateValidation {

    @Override
    public void validate(RestaurantDomain restaurantDomain) {
        if (restaurantDomain.isOpenAndCloseTimeInvalids()) {
            throw new ApplicationException("Open and close time are invalids");
        }
    }
}
