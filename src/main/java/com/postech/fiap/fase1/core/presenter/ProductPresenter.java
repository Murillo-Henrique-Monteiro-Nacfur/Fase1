package com.postech.fiap.fase1.core.presenter;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.dto.product.FileDTO;
import com.postech.fiap.fase1.core.dto.product.ProductDTO;
import com.postech.fiap.fase1.core.dto.product.ProductRequestDTO;
import com.postech.fiap.fase1.core.dto.product.ProductResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductPresenter {
    public ProductDomain requestToDomain(ProductRequestDTO productRequestDTO, FileDTO file) {
        return ProductDomain.builder()
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .photoUrl(productRequestDTO.getLinkImage())
                .restaurant(RestaurantDomain.builder().id(productRequestDTO.getIdRestaurant()).build())
                .file(file)
                .build();
    }

    public ProductDTO toDTO(ProductDomain productDomain) {
        return ProductDTO.builder()
                .id(productDomain.getId())
                .name(productDomain.getName())
                .description(productDomain.getDescription())
                .price(productDomain.getPrice())
                .photoUrl(productDomain.getPhotoUrl())
                .restaurantId(productDomain.getRestaurant().getId())
                .build();
    }

    public ProductResponseDTO toResponseDTO(ProductDomain productDomain) {
        return ProductResponseDTO.builder()
                .id(productDomain.getId())
                .name(productDomain.getName())
                .description(productDomain.getDescription())
                .price(productDomain.getPrice())
                .photoUrl(productDomain.getPhotoUrl())
                .build();
    }

    public ProductDomain toDomain(ProductDTO productDTO){
        return ProductDomain.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .photoUrl(productDTO.getPhotoUrl())
                .restaurant(RestaurantDomain.builder().id(productDTO.getRestaurantId()).build())
                .build();

    }
}
