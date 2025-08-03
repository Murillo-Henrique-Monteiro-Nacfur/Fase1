package com.postech.fiap.fase1.core.controllers.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.domain.usecase.product.ProductCreateUseCase;
import com.postech.fiap.fase1.core.dto.product.ProductRequestDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.gateway.product.ProductJpaGateway;
import com.postech.fiap.fase1.core.gateway.restaurant.RestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.presenter.ProductPresenter;

public class ProductCreateCoreController {
    private final ProductCreateUseCase productCreateUseCase;
    private final ProductPresenter productPresenter;

    public ProductCreateCoreController(DataSource dataSource, SessionSource sessionSource) {
        var productJpaGateway = ProductJpaGateway.build(dataSource);
        var sessionGateway = SessionGateway.build(sessionSource);
        var restaurantGateway = RestaurantGateway.build(dataSource);

        this.productCreateUseCase = new ProductCreateUseCase(productJpaGateway, sessionGateway, restaurantGateway);
        this.productPresenter = new ProductPresenter();
    }

    public ProductDomain createProduct(ProductRequestDTO productRequestDTO) {
        return productCreateUseCase.execute(productPresenter.requestToDomain(productRequestDTO));
    }

}
