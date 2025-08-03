package com.postech.fiap.fase1.core.domain.validation.product.implementation;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.domain.validation.product.ProductCreateValidation;
import com.postech.fiap.fase1.core.domain.validation.session.ISessionValidation;
import com.postech.fiap.fase1.core.domain.validation.session.SessionUserAllowedValidator;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.validation.product.ProductDeleteValidation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantOwnerVerification implements ProductCreateValidation, ProductDeleteValidation {
    private final ISessionValidation sessionValidation;

    public RestaurantOwnerVerification(ISessionGateway sessionGateway) {
        this.sessionValidation = new SessionUserAllowedValidator(sessionGateway);
    }

    @Override
    public void validation(ProductDomain productDomain) {
        sessionValidation.validate(productDomain.getRestaurant().getIdUserOwner());
    }
}
