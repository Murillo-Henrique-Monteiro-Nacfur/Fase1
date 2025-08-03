package com.postech.fiap.fase1.core.gateway.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.presenter.ProductPresenter;

public class ProductJpaGateway implements ProductGateway {
    private final DataSource dataSource;
    private final ProductPresenter productPresenter;

    public ProductJpaGateway(DataSource dataSource, ProductPresenter productPresenter) {
        this.dataSource = dataSource;
        this.productPresenter = productPresenter;
    }

    public static ProductJpaGateway build(DataSource dataSource) {return new ProductJpaGateway(dataSource, new ProductPresenter());}

    @Override
    public ProductDomain getOne(Long idUser) {
        return null;
    }

    @Override
    public ProductDomain create(ProductDomain productDomain) {
        return productPresenter.toDomain(dataSource.createProduct(productPresenter.toDTO(productDomain)));
    }

    @Override
    public ProductDomain update(ProductDomain productDomain) {
        return null;
    }
}
