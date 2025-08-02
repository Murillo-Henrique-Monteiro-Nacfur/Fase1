package com.postech.fiap.fase1.core.domain.usecase.address;

import com.postech.fiap.fase1.core.validation.session.SessionValidation;
import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.core.gateway.address.AddressGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressReadUseCase {

    private final AddressGateway addressGateway;
    private final SessionValidation sessionValidation;

    public AddressDomain getById(Long idAddress) {
        AddressDomain addressDomain = addressGateway.getById(idAddress)
                .orElseThrow(() -> new ApplicationException("Address not found"));
        sessionValidation.validate(addressDomain.getAddressable().getIdUserOwner());
        return addressDomain;
    }

    public Page<AddressDomain> getAllPaged(Pageable pageable) {
        return addressGateway.getAllPaged(pageable);
    }
}
