package com.postech.fiap.fase1.core.domain.usecase.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.gateway.product.ProductGateway;

public class ProductCreateUseCase {
    private final ProductGateway productGateway;

    public ProductCreateUseCase(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public ProductDomain execute(ProductDomain productDomain) {
        //TODO WILL CRIAR VALIDACOES
        return productGateway.create(productDomain);
    }


}
