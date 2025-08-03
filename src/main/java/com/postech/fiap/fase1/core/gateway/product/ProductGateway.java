package com.postech.fiap.fase1.core.gateway.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;

public interface ProductGateway {
    ProductDomain getOne(Long idUser);
    ProductDomain create(ProductDomain productDomain);
    ProductDomain update(ProductDomain productDomain);
}
