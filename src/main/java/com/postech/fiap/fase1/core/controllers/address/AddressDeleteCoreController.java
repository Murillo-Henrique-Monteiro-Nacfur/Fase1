package com.postech.fiap.fase1.core.controllers.address;

import com.postech.fiap.fase1.core.domain.usecase.address.AddressDeleteUseCase;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.gateway.address.AddressGateway;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;

public class AddressDeleteCoreController {
    private final AddressDeleteUseCase addressDeleteUseCase;

    public AddressDeleteCoreController(DataSource dataSource, SessionSource sessionSource) {
        var addressGateway = AddressGateway.build(dataSource);
        var sessionGateway = SessionGateway.build(sessionSource);

        addressDeleteUseCase = new AddressDeleteUseCase(addressGateway, sessionGateway);
    }

    public void delete(Long id) {
        addressDeleteUseCase.execute(id);
    }
}
