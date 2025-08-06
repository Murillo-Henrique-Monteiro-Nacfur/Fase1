package com.postech.fiap.fase1.core.domain.usecase.address;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.gateway.address.IAddressGateway;
import com.postech.fiap.fase1.core.gateway.restaurant.IRestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.domain.validation.address.implementation.AddressCreateToUserAllowedValidator;

public class AddressRestaurantCreateUseCase {

    private final IAddressGateway addressGateway;
    private final AddressCreateToUserAllowedValidator addressCreateToUserAllowedValidator;
    private final IRestaurantGateway restaurantGateway;

    public AddressRestaurantCreateUseCase(IAddressGateway addressGateway, ISessionGateway sessionGateway, IRestaurantGateway restaurantGateway) {
        this.addressGateway = addressGateway;
        this.restaurantGateway = restaurantGateway;
        addressCreateToUserAllowedValidator = new AddressCreateToUserAllowedValidator(sessionGateway);
    }

    public AddressDomain execute(AddressDomain addressDomain) {
        addressDomain.setId(null);
        RestaurantDomain restaurantDomain = restaurantGateway.getOneById(addressDomain.getAddressable().getId());
        if (restaurantDomain == null) {
            throw new com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException("Restaurante não encontrado");
        }
        addressDomain.setAddressable(restaurantDomain);
        addressCreateToUserAllowedValidator.validate(addressDomain);
        return addressGateway.createRestaurantAddress(addressDomain);
    }
}