package com.postech.fiap.fase1.core.controllers.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.domain.usecase.product.ProductCreateUseCase;
import com.postech.fiap.fase1.core.domain.usecase.product.ProductReadUseCase;
import com.postech.fiap.fase1.core.dto.product.ProductRequestDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.gateway.product.ProductJpaGateway;
import com.postech.fiap.fase1.core.gateway.restaurant.RestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.presenter.ProductPresenter;

import java.util.List;

public class ProductReadCoreController {
    private final ProductReadUseCase productReadUseCase;
    private final ProductPresenter productPresenter;

    public ProductReadCoreController(DataSource dataSource, SessionSource sessionSource) {
        var productJpaGateway = ProductJpaGateway.build(dataSource);
        var sessionGateway = SessionGateway.build(sessionSource);
        var restaurantGateway = RestaurantGateway.build(dataSource);

        this.productReadUseCase = new ProductReadUseCase(productJpaGateway, restaurantGateway);
        this.productPresenter = new ProductPresenter();
    }

    public ProductDomain getById(Long idProduct) {
        return productReadUseCase.getById(idProduct);
    }

    public List<ProductDomain> getByIdRestaurant(Long idRestaurant) {
        return productReadUseCase.getProductByIdRestaurant(idRestaurant);
    }

}
