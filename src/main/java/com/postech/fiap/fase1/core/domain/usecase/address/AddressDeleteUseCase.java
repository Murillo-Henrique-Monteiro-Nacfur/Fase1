package com.postech.fiap.fase1.core.domain.usecase.address;

import com.postech.fiap.fase1.core.gateway.address.IAddressGateway;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.domain.validation.session.SessionUserAllowedValidator;
import com.postech.fiap.fase1.core.domain.validation.session.ISessionValidation;

public class AddressDeleteUseCase {

    private final IAddressGateway iAddressGateway;
    private final ISessionValidation sessionValidation;

    public AddressDeleteUseCase(IAddressGateway addressGateway, ISessionGateway sessionGateway) {
        this.iAddressGateway = addressGateway;
        sessionValidation = new SessionUserAllowedValidator(sessionGateway);
    }
    public void execute(Long idAddress) {
        var addressDomainOld = iAddressGateway.getOneByid(idAddress);
        long userOwnerId = addressDomainOld.getAddressable().getIdUserOwner();
        sessionValidation.validate(userOwnerId);
        iAddressGateway.deleteById(idAddress);
    }
}
