package com.postech.fiap.fase1.core.controllers.product;

import com.postech.fiap.fase1.core.domain.usecase.product.ProductCreateUseCase;
import com.postech.fiap.fase1.core.dto.product.FileDTO;
import com.postech.fiap.fase1.core.dto.product.ProductRequestDTO;
import com.postech.fiap.fase1.core.dto.product.ProductResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.gateway.product.ProductJpaGateway;
import com.postech.fiap.fase1.core.gateway.restaurant.RestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.gateway.storage.StorageGateway;
import com.postech.fiap.fase1.core.presenter.ProductPresenter;
import com.postech.fiap.fase1.infrastructure.storage.StorageRepository;

public class ProductCreateCoreController {
    private final ProductCreateUseCase productCreateUseCase;
    private final ProductPresenter productPresenter;
    public ProductCreateCoreController(DataSource dataSource, SessionSource sessionSource, StorageRepository storageRepository) {
        var productJpaGateway = ProductJpaGateway.build(dataSource);
        var sessionGateway = SessionGateway.build(sessionSource);
        var restaurantGateway = RestaurantGateway.build(dataSource);
        var storageGateway = StorageGateway.build(storageRepository);

        this.productCreateUseCase = new ProductCreateUseCase(productJpaGateway, sessionGateway, restaurantGateway, storageGateway);
        this.productPresenter = new ProductPresenter();
    }

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO, FileDTO fileDTO) {
        return productPresenter.toResponseDTO(productCreateUseCase.execute(productPresenter.requestToDomain(productRequestDTO, fileDTO)));
    }
}
