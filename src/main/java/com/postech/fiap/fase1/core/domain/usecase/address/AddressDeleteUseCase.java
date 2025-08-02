package com.postech.fiap.fase1.core.domain.usecase.address;

import com.postech.fiap.fase1.core.validation.session.SessionValidation;
import com.postech.fiap.fase1.core.gateway.address.AddressGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressDeleteUseCase {

    private final AddressGateway addressGateway;
    private final SessionValidation sessionValidation;
    private final AddressReadUseCase addressReadUseCase;

    public void execute(Long idAddress) {
        var addressDomainOld = addressReadUseCase.getById(idAddress);
        long userOwnerId = addressDomainOld.getAddressable().getIdUserOwner();
        sessionValidation.validate(userOwnerId);
        addressGateway.deleteById(idAddress);
    }
}
