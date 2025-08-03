package com.postech.fiap.fase1.core.controllers.product;

import com.postech.fiap.fase1.core.domain.usecase.product.ProductDeleteUseCase;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.gateway.product.ProductGateway;
import com.postech.fiap.fase1.core.gateway.restaurant.RestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;

public class ProductDeleteCoreController {
    private final ProductDeleteUseCase productDeleteUseCase;

    public ProductDeleteCoreController(DataSource dataSource, SessionSource sessionSource) {
        var productJpaGateway = ProductGateway.build(dataSource);
        var sessionGateway = SessionGateway.build(sessionSource);
        var restaurantGateway = RestaurantGateway.build(dataSource);

        this.productDeleteUseCase = new ProductDeleteUseCase(productJpaGateway, sessionGateway, restaurantGateway);
    }

    public void delete(Long idProduct) {
        productDeleteUseCase.delete(idProduct);
    }
}
