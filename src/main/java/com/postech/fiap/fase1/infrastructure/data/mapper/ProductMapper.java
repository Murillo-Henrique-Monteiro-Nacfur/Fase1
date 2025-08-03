package com.postech.fiap.fase1.infrastructure.data.mapper;

import com.postech.fiap.fase1.core.dto.product.ProductDTO;
import com.postech.fiap.fase1.infrastructure.data.entity.Product;
import com.postech.fiap.fase1.infrastructure.data.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    public Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .photoUrl(productDTO.getPhotoUrl())
                .restaurant(Restaurant.builder().id(productDTO.getRestaurantId()).build())//TODO TROCAR
                .build();
    }

    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .photoUrl(product.getPhotoUrl())
                .restaurantId(product.getRestaurant().getId())
                .build();
    }

}
