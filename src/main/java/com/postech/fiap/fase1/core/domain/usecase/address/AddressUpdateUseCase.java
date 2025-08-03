package com.postech.fiap.fase1.core.domain.usecase.address;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.gateway.address.IAddressGateway;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.domain.validation.session.SessionUserAllowedValidator;
import com.postech.fiap.fase1.core.domain.validation.session.ISessionValidation;

public class AddressUpdateUseCase {

    private final IAddressGateway iAddressGateway;
    private final ISessionValidation sessionValidation;

    public AddressUpdateUseCase(IAddressGateway addressGateway, ISessionGateway sessionGateway) {
        iAddressGateway = addressGateway;
        sessionValidation = new SessionUserAllowedValidator(sessionGateway);
    }

    public AddressDomain execute(AddressDomain addressDomain) {
        var addressDomainOld = iAddressGateway.getOneByid(addressDomain.getId());
        long userOwnerId = addressDomainOld.getAddressable().getIdUserOwner();
        sessionValidation.validate(userOwnerId);
        return iAddressGateway.update(addressDomain);
    }
}
