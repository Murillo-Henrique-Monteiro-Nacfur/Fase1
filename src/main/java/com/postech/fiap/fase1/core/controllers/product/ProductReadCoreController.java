package com.postech.fiap.fase1.core.controllers.product;

import com.postech.fiap.fase1.core.domain.usecase.product.ProductReadUseCase;
import com.postech.fiap.fase1.core.dto.product.ProductResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.product.ProductGateway;
import com.postech.fiap.fase1.core.presenter.ProductPresenter;

import java.util.List;

public class ProductReadCoreController {
    private final ProductReadUseCase productReadUseCase;
    private final ProductPresenter productPresenter;

    public ProductReadCoreController(DataSource dataSource) {
        var productJpaGateway = ProductGateway.build(dataSource);
        this.productReadUseCase = new ProductReadUseCase(productJpaGateway);
        this.productPresenter = new ProductPresenter();
    }

    public ProductResponseDTO getById(Long idProduct) {
        return productPresenter.toResponseDTO(productReadUseCase.getById(idProduct));
    }

    public List<ProductResponseDTO> getByIdRestaurant(Long idRestaurant) {
        return productReadUseCase.getProductByIdRestaurant(idRestaurant).stream().map(productPresenter::toResponseDTO).toList();
    }

}
