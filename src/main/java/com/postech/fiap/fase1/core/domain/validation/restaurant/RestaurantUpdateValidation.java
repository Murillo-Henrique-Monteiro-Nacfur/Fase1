package com.postech.fiap.fase1.core.domain.validation.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;

public interface RestaurantUpdateValidation {

    void validate(RestaurantDomain restaurantDomain);
}
