package com.postech.fiap.fase1.core.validation.address.implementation;

import com.postech.fiap.fase1.core.validation.address.AddressCreateValidation;
import com.postech.fiap.fase1.core.validation.session.SessionValidation;
import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressCreateToUserAllowedValidator implements AddressCreateValidation {
    //private final SessionValidation sessionValidation;

    public void validate(AddressDomain addressDomain) {
       // sessionValidation.validate(addressDomain.getAddressable().getId());
    }
}
