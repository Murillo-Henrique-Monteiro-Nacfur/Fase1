package com.postech.fiap.fase1.webapi.data.mapper;

import com.postech.fiap.fase1.core.dto.product.ProductDTO;
import com.postech.fiap.fase1.webapi.data.entity.Product;
import com.postech.fiap.fase1.webapi.data.entity.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
    }

    @Test
    @DisplayName("Deve mapear corretamente ProductDTO para a entidade Product")
    void shouldCorrectlyMapProductDTOToProductEntity() {
        ProductDTO productDTO = ProductDTO.builder()
                .id(1L)
                .name("Test Burger")
                .description("A delicious test burger")
                .price(new BigDecimal("19.99"))
                .photoUrl("http://example.com/photo.jpg")
                .restaurantId(2L)
                .build();
        Product productEntity = productMapper.toEntity(productDTO);
        assertThat(productEntity).isNotNull();
        assertThat(productEntity.getId()).isEqualTo(productDTO.getId());
        assertThat(productEntity.getName()).isEqualTo(productDTO.getName());
        assertThat(productEntity.getDescription()).isEqualTo(productDTO.getDescription());
        assertThat(productEntity.getPrice()).isEqualTo(productDTO.getPrice());
        assertThat(productEntity.getPhotoUrl()).isEqualTo(productDTO.getPhotoUrl());

        assertThat(productEntity.getRestaurant()).isNotNull();
        assertThat(productEntity.getRestaurant().getId()).isEqualTo(productDTO.getRestaurantId());
    }

    @Test
    @DisplayName("Deve mapear corretamente a entidade Product para ProductDTO")
    void shouldCorrectlyMapProductEntityToProductDTO() {
        Restaurant restaurant = Restaurant.builder()
                .id(2L)
                .name("Test Restaurant")
                .build();

        Product productEntity = Product.builder()
                .id(1L)
                .name("Test Burger")
                .description("A delicious test burger")
                .price(new BigDecimal("19.99"))
                .photoUrl("http://example.com/photo.jpg")
                .restaurant(restaurant)
                .build();
        ProductDTO productDTO = productMapper.toDTO(productEntity);
        assertThat(productDTO).isNotNull();
        assertThat(productDTO.getId()).isEqualTo(productEntity.getId());
        assertThat(productDTO.getName()).isEqualTo(productEntity.getName());
        assertThat(productDTO.getDescription()).isEqualTo(productEntity.getDescription());
        assertThat(productDTO.getPrice()).isEqualTo(productEntity.getPrice());
        assertThat(productDTO.getPhotoUrl()).isEqualTo(productEntity.getPhotoUrl());
        assertThat(productDTO.getRestaurantId()).isEqualTo(productEntity.getRestaurant().getId());
    }
}