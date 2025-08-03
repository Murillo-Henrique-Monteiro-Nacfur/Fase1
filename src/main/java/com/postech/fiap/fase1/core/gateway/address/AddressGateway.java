package com.postech.fiap.fase1.core.gateway.address;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.dto.address.AddressDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.presenter.AddressPresenter;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class AddressGateway implements IAddressGateway {
    public static final String ADDRESS_NOT_FOUND = "Address not found";

    private final AddressPresenter addressPresenter;
    private final DataSource dataSource;

    private AddressGateway(DataSource dataSource, AddressPresenter addressPresenter) {
        this.dataSource = dataSource;
        this.addressPresenter = addressPresenter;
    }

    public static AddressGateway build(DataSource dataSource) {
        return new AddressGateway(dataSource, new AddressPresenter());
    }

    @Override
    public AddressDomain createUserAddress(AddressDomain addressDomain) {
        UserDTO userDTO = dataSource.getUserById(addressDomain.getAddressable().getId()).orElse(null);
        final AddressDTO address = addressPresenter.toAddressDTO(addressDomain, userDTO);
        return addressPresenter.toDomain(dataSource.createUserAddress(address));
    }

    @Override
    public AddressDomain createRestaurantAddress(AddressDomain addressDomain) {
        RestaurantDTO restaurantDTO = dataSource.getRestaurantById(addressDomain.getAddressable().getId()).orElse(null);
        final AddressDTO address = addressPresenter.toAddressDTO(addressDomain, restaurantDTO);
        return addressPresenter.toDomain(dataSource.createRestaurantAddress(address));
    }

    @Override
    public AddressDomain update(AddressDomain addressDomain) {
        var oldAddress = addressPresenter.toDTO(addressDomain);
        return addressPresenter.toDomain(dataSource.updateAddress(oldAddress));
    }

    public AddressDomain getOneByid(Long idAddress) {
        return dataSource.getAddressById(idAddress).map(addressPresenter::toDomain)
                .orElseThrow(() -> new ApplicationException(ADDRESS_NOT_FOUND));
    }

    @Override
    public Optional<AddressDomain> getById(Long idAddress) {
        return dataSource.getAddressById(idAddress).map(addressPresenter::toDomain).or(Optional::empty);
    }


    @Override
    public Page<AddressDomain> getAllPaged(Pageable pageable) {
        return dataSource.getAllAddressPaged(pageable).
                map(addressPresenter::toDomain);
    }

    @Override
    public void deleteById(Long idRestaurant) {
        dataSource.deleteAddressById(idRestaurant);
    }
}
