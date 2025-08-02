package com.postech.fiap.fase1.core.domain.usecase.address;

import com.postech.fiap.fase1.core.domain.usecase.user.UserReadUseCase;
import com.postech.fiap.fase1.core.validation.session.SessionValidation;
import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.address.AddressGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressUserCreateUseCase {

    private final AddressGateway addressGateway;
    private final UserReadUseCase userReadUseCase;
    private final SessionValidation sessionValidation;

    public AddressDomain execute(AddressDomain addressDomain) {
        sessionValidation.validate(addressDomain.getAddressable().getId());
        addressDomain.setId(null);
        UserDomain userDomain = userReadUseCase.getById(addressDomain.getAddressable().getId());
        addressDomain.setAddressable(userDomain);
        return addressGateway.create(addressDomain);
    }
}
