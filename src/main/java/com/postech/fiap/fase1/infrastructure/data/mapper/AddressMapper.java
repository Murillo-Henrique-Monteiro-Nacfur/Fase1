package com.postech.fiap.fase1.infrastructure.data.mapper;

import com.postech.fiap.fase1.core.dto.address.AddressDTO;
import com.postech.fiap.fase1.core.dto.address.AddressableDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import com.postech.fiap.fase1.infrastructure.data.entity.Address;
import com.postech.fiap.fase1.infrastructure.data.entity.Addressable;
import com.postech.fiap.fase1.infrastructure.data.entity.Restaurant;
import com.postech.fiap.fase1.infrastructure.data.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddressMapper {
    private final UserMapper userMapper;
    private final RestaurantMapper restaurantMapper;

    public Address toEntityRestaurant(AddressDTO addressDTO) {
        return Address.builder()
                .id(addressDTO.getId())
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .zipCode(addressDTO.getZipCode())
                .country(addressDTO.getCountry())
                .neighborhood(addressDTO.getNeighborhood())
                .addressable(restaurantMapper.toEntity(RestaurantDTO.builder().id(addressDTO.getAddressable().getId()).build()))
                .build();
    }

    public Address toEntityUser(AddressDTO addressDTO) {
        return Address.builder()
                .id(addressDTO.getId())
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .zipCode(addressDTO.getZipCode())
                .country(addressDTO.getCountry())
                .neighborhood(addressDTO.getNeighborhood())
                .addressable(userMapper.toEntity(UserDTO.builder().id(addressDTO.getAddressable().getId()).build()))
                .build();
    }

    public Address updateToEntity(Address address, AddressDTO addressDTO) {
        address.setStreet(addressDTO.getStreet());
        address.setNumber(addressDTO.getNumber());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZipCode(addressDTO.getZipCode());
        address.setCountry(addressDTO.getCountry());
        address.setNeighborhood(addressDTO.getNeighborhood());
        return address;
    }

    public AddressDTO toDTO(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .country(address.getCountry())
                .neighborhood(address.getNeighborhood())
                .addressable(getAddressableDTO(address.getAddressable()))
                .build();
    }

    private AddressableDTO getAddressableDTO(Addressable addressable) {
        if (addressable instanceof User user) {
            return userMapper.toDTO(user);
        }
        if (addressable instanceof Restaurant restaurant) {
            return restaurantMapper.toDTO(restaurant);
        }
        return null;
    }

    public List<AddressDTO> toDTO(List<Address> addresses) {
        return addresses.stream().map(this::toDTO).toList();
    }
}
