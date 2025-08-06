package com.postech.fiap.fase1.core.presenter;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.dto.product.FileDTO;
import com.postech.fiap.fase1.core.dto.product.ProductDTO;
import com.postech.fiap.fase1.core.dto.product.ProductRequestDTO;
import com.postech.fiap.fase1.core.dto.product.ProductResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductPresenterTest {
    private ProductPresenter presenter;

    @BeforeEach
    void setUp() {
        presenter = new ProductPresenter();
    }

    @Test
    void requestToDomain_shouldMapFields() {
        ProductRequestDTO request = ProductRequestDTO.builder()
                .name("Produto Teste")
                .description("Descrição")
                .price(BigDecimal.valueOf(10.5))
                .linkImage("http://img.com/produto.jpg")
                .idRestaurant(5L)
                .build();
        FileDTO file = FileDTO.builder().fileName("img.jpg").build();
        ProductDomain domain = presenter.requestToDomain(request, file);
        assertEquals(request.getName(), domain.getName());
        assertEquals(request.getDescription(), domain.getDescription());
        assertEquals(request.getPrice(), domain.getPrice());
        assertEquals(request.getLinkImage(), domain.getPhotoUrl());
        assertNotNull(domain.getRestaurant());
        assertEquals(request.getIdRestaurant(), domain.getRestaurant().getId());
        assertEquals(file, domain.getFile());
    }

    @Test
    void toDTO_shouldMapFields() {
        RestaurantDomain restaurant = RestaurantDomain.builder().id(7L).build();
        ProductDomain domain = ProductDomain.builder()
                .id(1L)
                .name("Produto Teste")
                .description("Descrição")
                .price(BigDecimal.valueOf(20.0))
                .photoUrl("http://img.com/produto2.jpg")
                .restaurant(restaurant)
                .build();
        ProductDTO dto = presenter.toDTO(domain);
        assertEquals(domain.getId(), dto.getId());
        assertEquals(domain.getName(), dto.getName());
        assertEquals(domain.getDescription(), dto.getDescription());
        assertEquals(domain.getPrice(), dto.getPrice());
        assertEquals(domain.getPhotoUrl(), dto.getPhotoUrl());
        assertEquals(domain.getRestaurant().getId(), dto.getRestaurantId());
    }

    @Test
    void toResponseDTO_shouldMapFields() {
        ProductDomain domain = ProductDomain.builder()
                .id(2L)
                .name("Produto Resp")
                .description("Resp Desc")
                .price(BigDecimal.valueOf(30.0))
                .photoUrl("http://img.com/resp.jpg")
                .build();
        ProductResponseDTO response = presenter.toResponseDTO(domain);
        assertEquals(domain.getId(), response.getId());
        assertEquals(domain.getName(), response.getName());
        assertEquals(domain.getDescription(), response.getDescription());
        assertEquals(domain.getPrice(), response.getPrice());
        assertEquals(domain.getPhotoUrl(), response.getPhotoUrl());
    }

    @Test
    void toDomain_fromDTO_shouldMapFields() {
        ProductDTO dto = ProductDTO.builder()
                .id(3L)
                .name("Produto DTO")
                .description("DTO Desc")
                .price(BigDecimal.valueOf(40.0))
                .photoUrl("http://img.com/dto.jpg")
                .restaurantId(9L)
                .build();
        ProductDomain domain = presenter.toDomain(dto);
        assertEquals(dto.getId(), domain.getId());
        assertEquals(dto.getName(), domain.getName());
        assertEquals(dto.getDescription(), domain.getDescription());
        assertEquals(dto.getPrice(), domain.getPrice());
        assertEquals(dto.getPhotoUrl(), domain.getPhotoUrl());
        assertNotNull(domain.getRestaurant());
        assertEquals(dto.getRestaurantId(), domain.getRestaurant().getId());
    }
}