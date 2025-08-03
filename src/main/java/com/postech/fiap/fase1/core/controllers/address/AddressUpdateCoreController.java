package com.postech.fiap.fase1.core.controllers.address;

import com.postech.fiap.fase1.core.domain.usecase.address.AddressUpdateUseCase;
import com.postech.fiap.fase1.core.dto.address.AddressRequestDTO;
import com.postech.fiap.fase1.core.dto.address.AddressResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.gateway.address.AddressGateway;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.presenter.AddressPresenter;

public class AddressUpdateCoreController {
    private final AddressUpdateUseCase addressUpdateUseCase;
    private final AddressPresenter addressPresenter;

    public AddressUpdateCoreController(DataSource dataSource, SessionSource sessionSource) {
        var addressGateway = AddressGateway.build(dataSource);
        var sessionGateway = SessionGateway.build(sessionSource);
        addressUpdateUseCase = new AddressUpdateUseCase(addressGateway, sessionGateway);
        this.addressPresenter = new AddressPresenter();
    }

    public AddressResponseDTO update(AddressRequestDTO addressRequestUpdateDTO) {
        return addressPresenter.toResponseDTO(addressUpdateUseCase.execute(addressPresenter.requestUpdateToInput(addressRequestUpdateDTO)));
    }
}