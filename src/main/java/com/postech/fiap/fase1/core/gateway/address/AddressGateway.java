package com.postech.fiap.fase1.core.gateway.address;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AddressGateway {
    AddressDomain create(AddressDomain addressDomain);
    AddressDomain update(AddressDomain addressDomain);
    Optional<AddressDomain> getById(Long idAddress);

    Page<AddressDomain> getAllPaged(Pageable pageable);

    void deleteById(Long idAddress);
}
