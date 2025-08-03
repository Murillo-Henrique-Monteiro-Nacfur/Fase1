package com.postech.fiap.fase1.core.validation.address.implementation;

import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.validation.address.AddressCreateValidation;
import com.postech.fiap.fase1.core.validation.session.SessionUserAllowedValidator;
import com.postech.fiap.fase1.core.validation.session.ISessionValidation;
import com.postech.fiap.fase1.core.domain.model.AddressDomain;


public class AddressCreateToUserAllowedValidator implements AddressCreateValidation {
    private final ISessionValidation sessionValidation;

    public AddressCreateToUserAllowedValidator(ISessionGateway sessionGateway) {
        this.sessionValidation = new SessionUserAllowedValidator(sessionGateway);
    }

    public void validate(AddressDomain addressDomain) {
       sessionValidation.validate(addressDomain.getAddressable().getIdUserOwner());
    }
}
