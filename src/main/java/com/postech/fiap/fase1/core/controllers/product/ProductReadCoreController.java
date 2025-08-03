package com.postech.fiap.fase1.core.controllers.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.domain.usecase.product.ProductReadUseCase;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.product.ProductGateway;

import java.util.List;

public class ProductReadCoreController {
    private final ProductReadUseCase productReadUseCase;

    public ProductReadCoreController(DataSource dataSource) {
        var productJpaGateway = ProductGateway.build(dataSource);

        this.productReadUseCase = new ProductReadUseCase(productJpaGateway);
    }

    public ProductDomain getById(Long idProduct) {
        return productReadUseCase.getById(idProduct);
    }

    public List<ProductDomain> getByIdRestaurant(Long idRestaurant) {
        return productReadUseCase.getProductByIdRestaurant(idRestaurant);
    }

}
