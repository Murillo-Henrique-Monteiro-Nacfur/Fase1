package com.postech.fiap.fase1.core.domain.usecase.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.domain.validation.product.ProductCreateValidation;
import com.postech.fiap.fase1.core.domain.validation.product.implementation.RestaurantOwnerVerification;
import com.postech.fiap.fase1.core.gateway.product.IProductGateway;
import com.postech.fiap.fase1.core.gateway.restaurant.IRestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;

public class ProductDeleteUseCase {
    private final IProductGateway productGateway;
    private final ProductCreateValidation productCreateValidation;
    private final IRestaurantGateway restaurantGateway;

    public ProductDeleteUseCase(IProductGateway productGateway, SessionGateway sessionGateway, IRestaurantGateway restaurantGateway) {
        this.productGateway = productGateway;
        this.productCreateValidation = new RestaurantOwnerVerification(sessionGateway);
        this.restaurantGateway = restaurantGateway;

    }

    public void delete(Long idProduct) {
        var product = productGateway.getProductById(idProduct);
        product.setRestaurant(restaurantGateway.getOneById(product.getRestaurant().getId()));
        validation(product);
        productGateway.delete(product);
    }

    private void validation(ProductDomain productDomain){
        productCreateValidation.validation(productDomain);
    }
}
