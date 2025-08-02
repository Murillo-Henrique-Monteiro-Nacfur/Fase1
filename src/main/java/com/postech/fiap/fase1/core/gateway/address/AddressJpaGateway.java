package com.postech.fiap.fase1.core.gateway.address;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.infrastructure.data.entity.Address;
import com.postech.fiap.fase1.infrastructure.data.mapper.AddressMapper;
import com.postech.fiap.fase1.infrastructure.data.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressJpaGateway implements AddressGateway {
    public static final String ADDRESS_NOT_FOUND = "Address not found";
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public AddressDomain create(AddressDomain addressDomain) {
        final Address address = null;//addressMapper.toEntity(addressDomain);
        return addressMapper.toDomain(addressRepository.save(address));
    }

    @Override
    public AddressDomain update(AddressDomain addressDomain) {
        var oldAddress = findById(addressDomain.getId());
        final Address address = null;//addressMapper.updateToEntity(addressDomain, oldAddress);
        return addressMapper.toDomain(addressRepository.save(address));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AddressDomain> getById(Long idRestaurent) {
        return addressRepository
                .findById(idRestaurent)
                .map(addressMapper::toDomain)
                .or(Optional::empty);
    }

    private Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(
                () -> new ApplicationException(ADDRESS_NOT_FOUND));
    }


    @Override
    @Transactional(readOnly = true)
    public Page<AddressDomain> getAllPaged(Pageable pageable) {
        return addressRepository.findAll(pageable).
                map(addressMapper::toDomain);
    }

    @Override
    public void deleteById(Long idRestaurant) {
        addressRepository.deleteById(idRestaurant);
    }
}
