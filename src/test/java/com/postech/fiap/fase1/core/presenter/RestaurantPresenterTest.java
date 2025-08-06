package com.postech.fiap.fase1.core.presenter;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantRequestDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantRequestUpdateDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantResponseDTO;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RestaurantPresenterTest {
    private RestaurantPresenter presenter;

    @BeforeEach
    void setUp() {
        presenter = new RestaurantPresenter();
    }

    @Test
    void toDomain_fromRequestDTO_shouldMapFields() {
        RestaurantRequestDTO dto = RestaurantRequestDTO.builder()
                .id(1L)
                .name("Restaurante Teste")
                .cnpj("12345678901234")
                .description("Desc")
                .cuisineType("Italiana")
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(22, 0))
                .userId(2L)
                .build();
        RestaurantDomain domain = presenter.toDomain(dto);
        assertEquals(dto.getId(), domain.getId());
        assertEquals(dto.getName(), domain.getName());
        assertEquals(dto.getCnpj(), domain.getCnpj());
        assertEquals(dto.getDescription(), domain.getDescription());
        assertEquals(dto.getCuisineType(), domain.getCuisineType());
        assertEquals(dto.getOpenTime(), domain.getOpenTime());
        assertEquals(dto.getCloseTime(), domain.getCloseTime());
        assertNotNull(domain.getUser());
        assertEquals(dto.getUserId(), domain.getUser().getId());
    }

    @Test
    void toDomain_fromRequestUpdateDTO_shouldMapFields() {
        RestaurantRequestUpdateDTO dto = RestaurantRequestUpdateDTO.builder()
                .id(1L)
                .name("Restaurante Atualizado")
                .cnpj("98765432109876")
                .description("Nova Desc")
                .cuisineType("Japonesa")
                .openTime(LocalTime.of(11, 0))
                .closeTime(LocalTime.of(23, 0))
                .build();
        RestaurantDomain domain = presenter.toDomain(dto);
        assertEquals(dto.getId(), domain.getId());
        assertEquals(dto.getName(), domain.getName());
        assertEquals(dto.getCnpj(), domain.getCnpj());
        assertEquals(dto.getDescription(), domain.getDescription());
        assertEquals(dto.getCuisineType(), domain.getCuisineType());
        assertEquals(dto.getOpenTime(), domain.getOpenTime());
        assertEquals(dto.getCloseTime(), domain.getCloseTime());
    }

    @Test
    void toDTO_shouldMapFields() {
        UserDomain user = UserDomain.builder().id(2L).build();
        RestaurantDomain domain = RestaurantDomain.builder()
                .id(1L)
                .name("Restaurante Teste")
                .cuisineType("Italiana")
                .description("Desc")
                .cnpj("12345678901234")
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(22, 0))
                .user(user)
                .build();
        RestaurantDTO dto = presenter.toDTO(domain);
        assertEquals(domain.getId(), dto.getId());
        assertEquals(domain.getName(), dto.getName());
        assertEquals(domain.getCuisineType(), dto.getCuisineType());
        assertEquals(domain.getDescription(), dto.getDescription());
        assertEquals(domain.getCnpj(), dto.getCnpj());
        assertEquals(domain.getOpenTime(), dto.getOpenTime());
        assertEquals(domain.getCloseTime(), dto.getCloseTime());
        assertNotNull(dto.getUser());
        assertEquals(domain.getUser().getId(), dto.getUser().getId());
    }

    @Test
    void toPageResponseDTOs_shouldMapPage() {
        RestaurantDomain domain = RestaurantDomain.builder()
                .id(1L)
                .name("Restaurante Teste")
                .cuisineType("Italiana")
                .description("Desc")
                .cnpj("12345678901234")
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(22, 0))
                .build();
        Page<RestaurantDomain> page = new PageImpl<>(Collections.singletonList(domain));
        Page<RestaurantResponseDTO> responsePage = presenter.toPageResponseDTOs(page);
        assertEquals(1, responsePage.getTotalElements());
        RestaurantResponseDTO dto = responsePage.getContent().getFirst();
        assertEquals(domain.getId(), dto.getId());
        assertEquals(domain.getName(), dto.getName());
    }

    @Test
    void toDomain_fromDTO_shouldMapFields() {
        UserDTO userDTO = UserDTO.builder().id(2L).build();
        RestaurantDTO dto = RestaurantDTO.builder()
                .id(1L)
                .name("Restaurante Teste")
                .description("Desc")
                .cuisineType("Italiana")
                .cnpj("12345678901234")
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(22, 0))
                .user(userDTO)
                .build();
        RestaurantDomain domain = presenter.toDomain(dto);
        assertEquals(dto.getId(), domain.getId());
        assertEquals(dto.getName(), domain.getName());
        assertEquals(dto.getDescription(), domain.getDescription());
        assertEquals(dto.getCuisineType(), domain.getCuisineType());
        assertEquals(dto.getCnpj(), domain.getCnpj());
        assertEquals(dto.getOpenTime(), domain.getOpenTime());
        assertEquals(dto.getCloseTime(), domain.getCloseTime());
        assertNotNull(domain.getUser());
        assertEquals(dto.getUser().getId(), domain.getUser().getId());
    }

    @Test
    void toResponseDTO_shouldMapFields() {
        RestaurantDomain domain = RestaurantDomain.builder()
                .id(1L)
                .name("Restaurante Teste")
                .cuisineType("Italiana")
                .description("Desc")
                .cnpj("12345678901234")
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(22, 0))
                .build();
        RestaurantResponseDTO dto = presenter.toResponseDTO(domain);
        assertEquals(domain.getId(), dto.getId());
        assertEquals(domain.getName(), dto.getName());
        assertEquals(domain.getCuisineType(), dto.getCuisineType());
        assertEquals(domain.getDescription(), dto.getDescription());
        assertEquals(domain.getCnpj(), dto.getCnpj());
        assertEquals(domain.getOpenTime(), dto.getOpenTime());
        assertEquals(domain.getCloseTime(), dto.getCloseTime());
    }
}