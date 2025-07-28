package com.postech.fiap.fase1.application.disassembler;

import com.postech.fiap.fase1.application.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.domain.model.RestaurantDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantDisassembler {
    private final UserDisassembler2 userDisassembler2;
    private final AddressDisassembler addressdisassembler;

    public RestaurantDTO toDTO(RestaurantDomain restaurantDomain) {
        return RestaurantDTO.builder()
                .id(restaurantDomain.getId())
                .name(restaurantDomain.getName())
                .cuisineType(restaurantDomain.getCuisineType())
                .description(restaurantDomain.getDescription())
                .cnpj(restaurantDomain.getCnpj())
                .user(userDisassembler2.toDTO(restaurantDomain.getUser()))
                .address(addressdisassembler.toDTO(restaurantDomain.getAddresses()))
                .openTime(restaurantDomain.getOpenTime())
                .closeTime(restaurantDomain.getCloseTime())
                .build();
    }
    public Page<RestaurantDTO> toPageDTOs(Page<RestaurantDomain> restaurantDomainPage) {
        return restaurantDomainPage.map(this::toDTO);
    }

}
