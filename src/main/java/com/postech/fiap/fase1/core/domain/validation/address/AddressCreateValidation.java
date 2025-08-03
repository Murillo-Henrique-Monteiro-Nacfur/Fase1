package com.postech.fiap.fase1.core.domain.validation.address;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;

public interface AddressCreateValidation {
    void validate(AddressDomain addressDomain);

}
