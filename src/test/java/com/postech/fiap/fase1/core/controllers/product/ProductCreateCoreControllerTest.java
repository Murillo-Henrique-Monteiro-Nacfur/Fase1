package com.postech.fiap.fase1.core.controllers.product;

import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.dto.product.FileDTO;
import com.postech.fiap.fase1.core.dto.product.ProductDTO;
import com.postech.fiap.fase1.core.dto.product.ProductRequestDTO;
import com.postech.fiap.fase1.core.dto.product.ProductResponseDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.webapi.infrastructure.storage.StorageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductCreateCoreControllerTest {
    @Mock
    private DataSource dataSource;
    @Mock
    private SessionSource sessionSource;
    @Mock
    private StorageRepository storageRepository;
    @InjectMocks
    private ProductCreateCoreController controller;

    @BeforeEach
    void setUp() {
        controller = new ProductCreateCoreController(dataSource, sessionSource, storageRepository);
        when(dataSource.getRestaurantById(anyLong())).thenReturn(
                Optional.ofNullable(RestaurantDTO.builder()
                        .id(1L)
                        .name("Test Restaurant")
                        .description("Test Description")
                        .cuisineType("Test Cuisine")
                        .cnpj("12345678901234")
                        .openTime(LocalTime.of(10, 0))
                        .closeTime(LocalTime.of(22, 0))
                        .user(UserDTO.builder().id(1L).build())
                        .address(Collections.emptyList())
                        .build())
        );
        when(sessionSource.getSessionDTO()).thenReturn(
                SessionDTO.builder()
                        .userId(1L)
                        .userRole("ADMIN")
                        .build()
        );


        when(dataSource.createProduct(any(ProductDTO.class))).thenAnswer(invocation -> {
            ProductDTO input = invocation.getArgument(0);
            return ProductDTO.builder()
                    .id(2L)
                    .name(input.getName())
                    .description(input.getDescription())
                    .price(input.getPrice())
                    .photoUrl(input.getPhotoUrl())
                    .restaurantId(input.getRestaurantId())
                    .build();
        });
    }

    @Test
    void testCreateProduct() {
        ProductRequestDTO requestDTO = new ProductRequestDTO(
                "New Product",
                "New Description",
                BigDecimal.valueOf(25.0),
                "image.gif",
                1L
        );
        FileDTO fileDTO = mock(FileDTO.class);
        when(fileDTO.getFileName()).thenReturn("test.jpg");

        String uploadedFileUrl = "http://example.com/uploaded/test.jpg";
        when(storageRepository.uploadFile(anyString(), any())).thenReturn(uploadedFileUrl);

        ProductResponseDTO response = controller.createProduct(requestDTO, fileDTO);

        assertNotNull(response);
        assertEquals(2L, response.getId());
        assertEquals("New Product", response.getName());
        assertEquals(uploadedFileUrl, response.getPhotoUrl());
    }
}