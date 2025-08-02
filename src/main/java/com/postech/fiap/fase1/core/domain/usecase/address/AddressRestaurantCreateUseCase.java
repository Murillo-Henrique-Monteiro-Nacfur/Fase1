package com.postech.fiap.fase1.core.domain.usecase.address;

import com.postech.fiap.fase1.core.domain.usecase.restaurant.RestaurantReadUseCase;
import com.postech.fiap.fase1.core.validation.session.SessionValidation;
import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.gateway.address.AddressGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressRestaurantCreateUseCase {

    private final AddressGateway addressGateway;
    private final RestaurantReadUseCase restaurantReadUseCase;
    //private final SessionValidation sessionValidation;

    public AddressDomain execute(AddressDomain addressDomain) {
        addressDomain.setId(null);
        RestaurantDomain restaurantDomain = restaurantReadUseCase.getById(addressDomain.getAddressable().getId());
        addressDomain.setAddressable(restaurantDomain);
        //sessionValidation.validate(restaurantDomain.getUser().getId());
        return addressGateway.create(addressDomain);
    }
}
