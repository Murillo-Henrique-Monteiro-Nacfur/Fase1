package com.postech.fiap.fase1.core.domain.usecase.address;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.domain.validation.session.SessionUserAllowedValidator;
import com.postech.fiap.fase1.core.domain.validation.session.ISessionValidation;
import com.postech.fiap.fase1.core.gateway.address.IAddressGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public class AddressReadUseCase {

    private final IAddressGateway iAddressGateway;
    private final ISessionValidation sessionValidation;

    public AddressReadUseCase(IAddressGateway addressGateway, ISessionGateway sessionGateway) {
        iAddressGateway = addressGateway;
        sessionValidation = new SessionUserAllowedValidator(sessionGateway);
    }

    public AddressDomain getById(Long idAddress) {
        AddressDomain addressDomain = iAddressGateway.getOneByid(idAddress);
        sessionValidation.validate(addressDomain.getAddressable().getIdUserOwner());
        return addressDomain;
    }

    public Page<AddressDomain> getAllPaged(Pageable pageable) {
        return iAddressGateway.getAllPaged(pageable);
    }
}
