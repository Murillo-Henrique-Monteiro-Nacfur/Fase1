package com.postech.fiap.fase1.core.controllers.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.dto.product.ProductDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductReadCoreControllerTest {
    @Mock
    private DataSource dataSource;
    @InjectMocks
    private ProductReadCoreController productReadCoreController;

    private ProductDTO createValidProductDTO() {
        ProductDTO dto = ProductDTO.builder().build();
        dto.setId(1L);
        dto.setName("Test Product");
        dto.setDescription("Description");
        dto.setPrice(BigDecimal.valueOf(10));
        dto.setRestaurantId(100L);
        return dto;
    }

    @BeforeEach
    public void setUp() {
        productReadCoreController = new ProductReadCoreController(dataSource);
    }

    @Test
    void testGetById() {
        ProductDTO productDTO = createValidProductDTO();
        when(dataSource.getById(anyLong())).thenReturn(productDTO);

        Long id = 1L;
        ProductDomain result = productReadCoreController.getById(id);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Product", result.getName());
    }

    @Test
    void testGetByIdRestaurant() {
        ProductDTO productDTO = createValidProductDTO();
        when(dataSource.getProductByIdRestaurant(anyLong())).thenReturn(Collections.singletonList(productDTO));

        Long idRestaurant = 100L;
        List<ProductDomain> result = productReadCoreController.getByIdRestaurant(idRestaurant);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.getFirst().getId());
        assertEquals("Test Product", result.getFirst().getName());
    }
}