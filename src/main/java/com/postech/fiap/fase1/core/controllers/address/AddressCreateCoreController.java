package com.postech.fiap.fase1.core.controllers.address;

import com.postech.fiap.fase1.core.domain.usecase.address.AddressRestaurantCreateUseCase;
import com.postech.fiap.fase1.core.domain.usecase.address.AddressUserCreateUseCase;
import com.postech.fiap.fase1.core.dto.address.AddressRequestDTO;
import com.postech.fiap.fase1.core.dto.address.AddressResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.gateway.address.AddressGateway;
import com.postech.fiap.fase1.core.gateway.restaurant.RestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.presenter.AddressPresenter;

public class AddressCreateCoreController {

    private final AddressUserCreateUseCase addressUserCreateUseCase;
    private final AddressRestaurantCreateUseCase addressRestaurantCreateUseCase;
    private final AddressPresenter addressPresenter;

    public AddressCreateCoreController(DataSource dataSource, SessionSource sessionSource) {
        var addressGateway = AddressGateway.build(dataSource);
        var restaurantGateway = RestaurantGateway.build(dataSource);
        var sessionGateway = SessionGateway.build(sessionSource);

        this.addressUserCreateUseCase = new AddressUserCreateUseCase(addressGateway, sessionGateway);
        addressRestaurantCreateUseCase = new AddressRestaurantCreateUseCase(addressGateway, sessionGateway, restaurantGateway);
        this.addressPresenter = new AddressPresenter();
    }

    public AddressCreateCoreController(AddressUserCreateUseCase addressUserCreateUseCase, AddressRestaurantCreateUseCase addressRestaurantCreateUseCase, AddressPresenter addressPresenter) {
        this.addressUserCreateUseCase = addressUserCreateUseCase;
        this.addressRestaurantCreateUseCase = addressRestaurantCreateUseCase;
        this.addressPresenter = addressPresenter;
    }

    public AddressResponseDTO createUserAddress(AddressRequestDTO addressRequestDTO, Long idUser) {
        return addressPresenter.toResponseDTO(addressUserCreateUseCase.execute(addressPresenter.requestToAddressUserDomain(addressRequestDTO, idUser)));
    }

    public AddressResponseDTO createRestaurantAddress(AddressRequestDTO addressRequestDTO, Long idRestaurant) {
        return addressPresenter.toResponseDTO(addressRestaurantCreateUseCase.execute(addressPresenter.requestToAddressRestaurantDomain(addressRequestDTO, idRestaurant)));
    }
}