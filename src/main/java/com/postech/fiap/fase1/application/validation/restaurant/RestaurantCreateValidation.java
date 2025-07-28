package com.postech.fiap.fase1.application.validation.restaurant;

import com.postech.fiap.fase1.domain.model.RestaurantDomain;

public interface RestaurantCreateValidation {

    void validate(RestaurantDomain restaurantDomain);
}
