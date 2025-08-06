package com.postech.fiap.fase1.core.controllers.address;

import com.postech.fiap.fase1.core.domain.usecase.address.AddressReadUseCase;
import com.postech.fiap.fase1.core.dto.address.AddressResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.gateway.address.AddressGateway;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.presenter.AddressPresenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AddressReadCoreController {
    private final AddressReadUseCase addressReadUseCase;
    private final AddressPresenter addressPresenter;

    public AddressReadCoreController(DataSource dataSource, SessionSource sessionSource) {
        var addressGateway = AddressGateway.build(dataSource);
        var sessionGateway = SessionGateway.build(sessionSource);
        addressReadUseCase = new AddressReadUseCase(addressGateway, sessionGateway);
        this.addressPresenter = new AddressPresenter();
    }

    public AddressReadCoreController(AddressReadUseCase addressReadUseCase, AddressPresenter addressPresenter) {
        this.addressReadUseCase = addressReadUseCase;
        this.addressPresenter = addressPresenter;
    }

    public AddressResponseDTO findById(Long id) {
        return addressPresenter.toResponseDTO(addressReadUseCase.getById(id));
    }

    public Page<AddressResponseDTO> findAllPaged(Pageable pageable) {
        return addressPresenter.toResponseDTO(addressReadUseCase.getAllPaged(pageable));
    }

}