package com.postech.fiap.fase1.core.validation.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;

public interface RestaurantUpdateValidation {

    void validate(RestaurantDomain restaurantDomain);
}
