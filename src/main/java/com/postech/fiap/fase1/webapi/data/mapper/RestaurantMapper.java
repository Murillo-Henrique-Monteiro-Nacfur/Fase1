package com.postech.fiap.fase1.webapi.data.mapper;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.webapi.data.entity.Restaurant;
import com.postech.fiap.fase1.webapi.data.entity.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class RestaurantMapper {
    private final UserMapper userMapper;

    @Lazy
    public RestaurantMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Restaurant toEntity(RestaurantDTO restaurantDomain) {
        return isNull(restaurantDomain) ? null : Restaurant.builder()
                .id(restaurantDomain.getId())
                .name(restaurantDomain.getName())
                .cuisineType(restaurantDomain.getCuisineType())
                .description(restaurantDomain.getDescription())
                .cnpj(restaurantDomain.getCnpj())
                .openTime(restaurantDomain.getOpenTime())
                .closeTime(restaurantDomain.getCloseTime())
                .user(getUser(restaurantDomain))
                .build();
    }

    private User getUser(RestaurantDTO restaurantDomain) {
        return isNull(restaurantDomain.getUser()) ? null : userMapper.toEntity(restaurantDomain.getUser());
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
                .build();
    }

    public Restaurant updateToEntity(RestaurantDTO restaurantDTO, Restaurant restaurant) {
        restaurant.setName(restaurantDTO.getName());
        restaurant.setDescription(restaurantDTO.getDescription());
        restaurant.setCuisineType(restaurantDTO.getCuisineType());
        restaurant.setCnpj(restaurantDTO.getCnpj());
        restaurant.setOpenTime(restaurantDTO.getOpenTime());
        restaurant.setCloseTime(restaurantDTO.getCloseTime());
        return restaurant;
    }

    public RestaurantDTO toDTO(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .cuisineType(restaurant.getCuisineType())
                .description(restaurant.getDescription())
                .cnpj(restaurant.getCnpj())
                .openTime(restaurant.getOpenTime())
                .closeTime(restaurant.getCloseTime())
                .user(isNull(restaurant.getUser()) ? null : userMapper.toDTO(restaurant.getUser()))
                .build();
    }

    public RestaurantDTO toDTO(RestaurantDTO restaurant) {
        return RestaurantDTO.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .cuisineType(restaurant.getCuisineType())
                .description(restaurant.getDescription())
                .cnpj(restaurant.getCnpj())
                .openTime(restaurant.getOpenTime())
                .closeTime(restaurant.getCloseTime())
                .build();
    }
}
