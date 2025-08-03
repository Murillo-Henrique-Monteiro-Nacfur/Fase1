package com.postech.fiap.fase1.core.presenter;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantRequestDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantRequestUpdateDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantResponseDTO;
import org.springframework.data.domain.Page;

import static java.util.Objects.isNull;

public class RestaurantPresenter {
    private final UserPresenter userPresenter;
    private final AddressPresenter addressPresenter;

    public RestaurantPresenter() {
        this.userPresenter = new UserPresenter();
        this.addressPresenter = new AddressPresenter();
    }

    public RestaurantDomain toDomain(RestaurantRequestDTO restaurantRequestDto) {
        return RestaurantDomain.builder()
                .id(restaurantRequestDto.getId())
                .name(restaurantRequestDto.getName())
                .cnpj(restaurantRequestDto.getCnpj())
                .description(restaurantRequestDto.getDescription())
                .cuisineType(restaurantRequestDto.getCuisineType())
                .openTime(restaurantRequestDto.getOpenTime())
                .closeTime(restaurantRequestDto.getCloseTime())
                .user(UserDomain.builder().id(restaurantRequestDto.getUserId()).build())
                .build();
    }

    public RestaurantDomain toDomain(RestaurantRequestUpdateDTO restaurantRequestUpdateDTO) {
        return RestaurantDomain.builder()
                .id(restaurantRequestUpdateDTO.getId())
                .name(restaurantRequestUpdateDTO.getName())
                .cnpj(restaurantRequestUpdateDTO.getCnpj())
                .description(restaurantRequestUpdateDTO.getDescription())
                .cuisineType(restaurantRequestUpdateDTO.getCuisineType())
                .openTime(restaurantRequestUpdateDTO.getOpenTime())
                .closeTime(restaurantRequestUpdateDTO.getCloseTime())
                .build();
    }

    public RestaurantDTO toDTO(RestaurantDomain restaurantDomain) {
        return RestaurantDTO.builder()
                .id(restaurantDomain.getId())
                .name(restaurantDomain.getName())
                .cuisineType(restaurantDomain.getCuisineType())
                .description(restaurantDomain.getDescription())
                .cnpj(restaurantDomain.getCnpj())
                .openTime(restaurantDomain.getOpenTime())
                .closeTime(restaurantDomain.getCloseTime())
                .user(isNull(restaurantDomain.getUser()) ? null : userPresenter.toDTO(restaurantDomain.getUser()))
                .build();
    }

    public Page<RestaurantResponseDTO> toPageResponseDTOs(Page<RestaurantDomain> restaurantDomainPage) {
        return restaurantDomainPage.map(this::toResponseDTO);
    }

    public RestaurantDomain toDomain(RestaurantDTO restaurantDTO) {
        return RestaurantDomain.builder()
                .id(restaurantDTO.getId())
                .name(restaurantDTO.getName())
                .description(restaurantDTO.getDescription())
                .cuisineType(restaurantDTO.getCuisineType())
                .cnpj(restaurantDTO.getCnpj())
                .openTime(restaurantDTO.getOpenTime())
                .closeTime(restaurantDTO.getCloseTime())
                .user(isNull(restaurantDTO.getUser()) ? null : userPresenter.toDomain(restaurantDTO.getUser()))
                .addresses(isNull(restaurantDTO.getAddress()) ? null : addressPresenter.toDomain(restaurantDTO.getAddress()))
                .build();
    }

    public RestaurantResponseDTO toResponseDTO(RestaurantDomain restaurantDomain) {
        return RestaurantResponseDTO.builder()
                .id(restaurantDomain.getId())
                .name(restaurantDomain.getName())
                .cuisineType(restaurantDomain.getCuisineType())
                .description(restaurantDomain.getDescription())
                .cnpj(restaurantDomain.getCnpj())
                .openTime(restaurantDomain.getOpenTime())
                .closeTime(restaurantDomain.getCloseTime())
                .build();
    }
}