package com.postech.fiap.fase1.core.gateway.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;

import java.util.List;

public interface IProductGateway {
    ProductDomain getProductById(Long idUser);
    List<ProductDomain> getProductByIdRestaurant(Long idRestaurant);
    ProductDomain create(ProductDomain productDomain);
    void delete(ProductDomain productDomain);
}
