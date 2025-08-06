package com.postech.fiap.fase1.core.dto.restaurant;

import com.postech.fiap.fase1.core.dto.address.AddressDTO;
import com.postech.fiap.fase1.core.dto.address.AddressableDTO;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RestaurantDTO implements AddressableDTO {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private String cnpj;
    private LocalTime openTime;
    private LocalTime closeTime;
    private UserDTO user;
    private List<AddressDTO> address;
}
