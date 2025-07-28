package com.postech.fiap.fase1.application.assembler;

import com.postech.fiap.fase1.application.dto.restaurant.RestaurantRequestDTO;
import com.postech.fiap.fase1.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.domain.model.UserDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RestaurantAssembler {

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
                .addresses(List.of(AddressAssembler.requestToModel(restaurantRequestDto.getAddress())))
                .build();
    }
}