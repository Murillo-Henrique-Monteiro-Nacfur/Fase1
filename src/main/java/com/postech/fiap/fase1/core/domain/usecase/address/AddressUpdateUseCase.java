package com.postech.fiap.fase1.core.domain.usecase.address;

import com.postech.fiap.fase1.core.validation.session.SessionValidation;
import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.gateway.address.AddressGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressUpdateUseCase {

    private final AddressGateway addressGateway;
    //private final SessionValidation sessionValidation;
    private final AddressReadUseCase addressReadUseCase;

    public AddressDomain execute(AddressDomain addressDomain) {
        var addressDomainOld = addressReadUseCase.getById(addressDomain.getId());
        long userOwnerId = addressDomainOld.getAddressable().getIdUserOwner();
        //sessionValidation.validate(userOwnerId);
        return addressGateway.update(addressDomain);
    }
}
