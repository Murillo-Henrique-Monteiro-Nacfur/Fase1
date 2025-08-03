package com.postech.fiap.fase1.core.domain.usecase.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.gateway.product.ProductGateway;
import com.postech.fiap.fase1.core.gateway.restaurant.IRestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.gateway.storage.IStorageGateway;
import com.postech.fiap.fase1.core.validation.product.ProductCreateValidation;
import com.postech.fiap.fase1.core.validation.product.implementation.RestaurantOwnerVerification;

public class ProductCreateUseCase {
    private final ProductGateway productGateway;
    private final ProductCreateValidation productCreateValidation;
    private final IRestaurantGateway restaurantGateway;
    private final IStorageGateway iStorageGateway;

    public ProductCreateUseCase(ProductGateway productGateway, SessionGateway sessionGateway, IRestaurantGateway restaurantGateway, IStorageGateway iStorageGateway) {
        this.productGateway = productGateway;
        this.productCreateValidation = new RestaurantOwnerVerification(sessionGateway);
        this.restaurantGateway = restaurantGateway;
        this.iStorageGateway = iStorageGateway;
    }

    public ProductDomain execute(ProductDomain productDomain) {
        var restaurant = restaurantGateway.getOneById(productDomain.getRestaurant().getId());
        productDomain.setRestaurant(restaurant);
        validation(productDomain);
        String url = iStorageGateway.uploadFile(productDomain.getFile());
        productDomain.setPhotoUrl(url);
        return productGateway.create(productDomain);
    }

    private void validation(ProductDomain productDomain) {
        productCreateValidation.validation(productDomain);
    }
}
