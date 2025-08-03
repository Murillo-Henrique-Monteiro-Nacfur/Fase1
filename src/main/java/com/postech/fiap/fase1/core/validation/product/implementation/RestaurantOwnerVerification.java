package com.postech.fiap.fase1.core.validation.product.implementation;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.validation.product.ProductCreateValidation;
import com.postech.fiap.fase1.core.validation.session.ISessionValidation;
import com.postech.fiap.fase1.core.validation.session.SessionUserAllowedValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantOwnerVerification implements ProductCreateValidation {
    private final ISessionValidation sessionValidation;

    public RestaurantOwnerVerification(ISessionGateway sessionGateway) {
        this.sessionValidation = new SessionUserAllowedValidator(sessionGateway);
    }

    @Override
    public void validation(ProductDomain productDomain) {
        sessionValidation.validate(productDomain.getRestaurant().getIdUserOwner());
    }
}
