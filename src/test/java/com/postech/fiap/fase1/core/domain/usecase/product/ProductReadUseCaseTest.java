package com.postech.fiap.fase1.core.domain.usecase.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.gateway.product.IProductGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductReadUseCaseTest {

    @Mock
    private IProductGateway productGateway;

    private ProductReadUseCase productReadUseCase;

    @BeforeEach
    void setUp() {
        productReadUseCase = new ProductReadUseCase(productGateway);
    }

    @Test
    @DisplayName("Deve retornar produto por ID com sucesso")
    void getById_shouldReturnProduct_whenIdExists() {
        Long productId = 1L;
        ProductDomain expectedProduct = new ProductDomain();
        expectedProduct.setId(productId);

        when(productGateway.getProductById(productId)).thenReturn(expectedProduct);

        ProductDomain result = productReadUseCase.getById(productId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(productId);
        verify(productGateway).getProductById(productId);
    }

    @Test
    @DisplayName("Deve retornar lista de produtos por ID do restaurante com sucesso")
    void getProductByIdRestaurant_shouldReturnProductList_whenRestaurantIdExists() {
        Long restaurantId = 1L;
        ProductDomain product = new ProductDomain();
        product.setId(1L);
        List<ProductDomain> expectedProducts = Collections.singletonList(product);

        when(productGateway.getProductByIdRestaurant(restaurantId)).thenReturn(expectedProducts);

        List<ProductDomain> result = productReadUseCase.getProductByIdRestaurant(restaurantId);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getId()).isEqualTo(product.getId());
        verify(productGateway).getProductByIdRestaurant(restaurantId);
    }
}