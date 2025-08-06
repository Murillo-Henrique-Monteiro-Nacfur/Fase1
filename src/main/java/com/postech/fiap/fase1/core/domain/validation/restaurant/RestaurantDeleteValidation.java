package com.postech.fiap.fase1.core.domain.validation.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;

public interface RestaurantDeleteValidation {

    void validate(RestaurantDomain restaurantDomain);
}
