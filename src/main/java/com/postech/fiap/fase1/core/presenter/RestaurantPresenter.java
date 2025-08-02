package com.postech.fiap.fase1.core.presenter;

import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantRequestDTO;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class RestaurantPresenter {
    private final UserPresenter userPresenter;
    private final AddressPresenter addressPresenter;
    public RestaurantDomain toModel(RestaurantRequestDTO restaurantRequestDto) {
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

    public RestaurantDTO toDTO(RestaurantDomain restaurantDomain) {
        return RestaurantDTO.builder()
                .id(restaurantDomain.getId())
                .name(restaurantDomain.getName())
                .cuisineType(restaurantDomain.getCuisineType())
                .description(restaurantDomain.getDescription())
                .cnpj(restaurantDomain.getCnpj())
                .address(isNull(restaurantDomain.getAddresses()) ? null : addressPresenter.toDTO(restaurantDomain.getAddresses()))
                .openTime(restaurantDomain.getOpenTime())
                .closeTime(restaurantDomain.getCloseTime())
                .build();
    }
    public Page<RestaurantDTO> toPageDTOs(Page<RestaurantDomain> restaurantDomainPage) {
        return restaurantDomainPage.map(this::toDTO);
    }
}