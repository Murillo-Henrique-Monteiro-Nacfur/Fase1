package com.postech.fiap.fase1.core.domain.usecase.address;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.gateway.address.IAddressGateway;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.domain.validation.address.AddressCreateValidation;
import com.postech.fiap.fase1.core.domain.validation.address.implementation.AddressCreateToUserAllowedValidator;

public class AddressUserCreateUseCase {

    private final IAddressGateway iAddressGateway;
    private final AddressCreateValidation addressCreateValidation;

    public AddressUserCreateUseCase(IAddressGateway addressGateway, ISessionGateway sessionGateway) {
        iAddressGateway = addressGateway;
        addressCreateValidation = new AddressCreateToUserAllowedValidator(sessionGateway);
    }

    public AddressDomain execute(AddressDomain addressDomain) {
        addressCreateValidation.validate(addressDomain);
        addressDomain.setId(null);
        return iAddressGateway.createUserAddress(addressDomain);
    }
}
