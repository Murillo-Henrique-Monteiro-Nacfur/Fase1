package com.postech.fiap.fase1.infrastructure.persistence.mapper;

import com.postech.fiap.fase1.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.infrastructure.persistence.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class RestaurantMapper {
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;

    public Restaurant toEntity(RestaurantDomain restaurantDomain) {
        return Restaurant.builder()
                .id(restaurantDomain.getId())
                .name(restaurantDomain.getName())
                .cuisineType(restaurantDomain.getCuisineType())
                .description(restaurantDomain.getDescription())
                .cnpj(restaurantDomain.getCnpj())
                .openTime(restaurantDomain.getOpenTime())
                .closeTime(restaurantDomain.getCloseTime())
                .user(isNull(restaurantDomain.getUser()) ? null : userMapper.toEntity(restaurantDomain.getUser()))
                .addresses(isNull(restaurantDomain.getAddresses()) ? null : addressMapper.toEntity(restaurantDomain.getAddresses()))
                .build();
    }

    public RestaurantDomain toDomain(Restaurant restaurant) {
        return RestaurantDomain.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .description(restaurant.getDescription())
                .cuisineType(restaurant.getCuisineType())
                .cnpj(restaurant.getCnpj())
                .openTime(restaurant.getOpenTime())
                .closeTime(restaurant.getCloseTime())
                .user(isNull(restaurant.getUser()) ? null : userMapper.toDomain(restaurant.getUser()))
                .addresses(isNull(restaurant.getAddresses()) ? null :  addressMapper.toDomain(restaurant.getAddresses()))
                .build();
    }
}
