package com.postech.fiap.fase1.core.controllers.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.domain.usecase.product.ProductCreateUseCase;
import com.postech.fiap.fase1.core.dto.product.ProductRequestDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.product.ProductJpaGateway;
import com.postech.fiap.fase1.core.presenter.ProductPresenter;

public class ProductCreateCoreController {
    private final ProductCreateUseCase productCreateUseCase;
    private final ProductPresenter productPresenter;

    public ProductCreateCoreController(DataSource dataSource) {
        var productJpaGateway = new ProductJpaGateway(dataSource);
        this.productCreateUseCase = new ProductCreateUseCase(productJpaGateway);
        this.productPresenter = new ProductPresenter();
    }

    public ProductDomain createProduct(ProductRequestDTO productRequestDTO) {
        return productCreateUseCase.execute(productPresenter.requestToDomain(productRequestDTO));
    }

}
