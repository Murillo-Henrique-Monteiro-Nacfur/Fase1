package com.postech.fiap.fase1.core.domain.usecase.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.gateway.product.ProductGateway;
import com.postech.fiap.fase1.core.gateway.restaurant.IRestaurantGateway;

import java.util.List;

public class ProductReadUseCase {
    private final ProductGateway productGateway;
    private final IRestaurantGateway restaurantGateway;

    public ProductReadUseCase(ProductGateway productGateway, IRestaurantGateway restaurantGateway) {
        this.productGateway = productGateway;
        this.restaurantGateway = restaurantGateway;

    }

    public ProductDomain getById(Long idProduct) {
        return productGateway.getProductById(idProduct);
    }

    public List<ProductDomain> getProductByIdRestaurant(Long idRestaurant) {
        return productGateway.getProductByIdRestaurant(idRestaurant);
    }
}
