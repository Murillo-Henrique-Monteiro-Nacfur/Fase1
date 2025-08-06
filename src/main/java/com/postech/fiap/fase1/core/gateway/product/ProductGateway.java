package com.postech.fiap.fase1.core.gateway.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.presenter.ProductPresenter;

import java.util.List;

public class ProductGateway implements IProductGateway {
    private final DataSource dataSource;
    private final ProductPresenter productPresenter;

    public ProductGateway(DataSource dataSource, ProductPresenter productPresenter) {
        this.dataSource = dataSource;
        this.productPresenter = productPresenter;
    }

    public static ProductGateway build(DataSource dataSource) {
        return new ProductGateway(dataSource, new ProductPresenter());
    }

    @Override
    public ProductDomain getProductById(Long idProduct) {
        return productPresenter.toDomain(dataSource.getById(idProduct));
    }

    @Override
    public List<ProductDomain> getProductByIdRestaurant(Long idRestaurant) {
        return dataSource.getProductByIdRestaurant(idRestaurant).stream().map(productPresenter::toDomain).toList();
    }


    @Override
    public ProductDomain create(ProductDomain productDomain) {
        return productPresenter.toDomain(dataSource.createProduct(productPresenter.toDTO(productDomain)));
    }

    @Override
    public void delete(ProductDomain productDomain) {
        dataSource.deleteProductById(productDomain.getId());
    }

}
