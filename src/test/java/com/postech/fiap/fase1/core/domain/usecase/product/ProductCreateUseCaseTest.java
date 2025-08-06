package com.postech.fiap.fase1.core.domain.usecase.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.dto.product.FileDTO;
import com.postech.fiap.fase1.core.gateway.product.IProductGateway;
import com.postech.fiap.fase1.core.gateway.restaurant.IRestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.gateway.storage.IStorageGateway;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductCreateUseCaseTest {

    @Mock
    private IProductGateway productGateway;

    @Mock
    private SessionGateway sessionGateway;

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Mock
    private IStorageGateway storageGateway;

    @InjectMocks
    private ProductCreateUseCase productCreateUseCase;

    @Mock
    private SessionDTO sessionDTO;

    @BeforeEach
    void setUp() {
    }

    @Test
    void execute_deveCriarProduto_quandoUsuarioForAutorizado() {
        Long restaurantId = 1L;
        Long userId = 10L;
        String fileUrl = "http://example.com/image.jpg";

        UserDomain owner = UserDomain.builder().id(userId).build();
        RestaurantDomain restaurant = RestaurantDomain.builder().id(restaurantId).user(owner).build();
        FileDTO fileDTO = new FileDTO("test.jpg", new byte[0]);
        ProductDomain productToCreate = ProductDomain.builder()
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.TEN)
                .restaurant(RestaurantDomain.builder().id(restaurantId).build())
                .file(fileDTO)
                .build();

        when(sessionGateway.getSessionDTO()).thenReturn(sessionDTO);
        when(sessionDTO.isNotAdmin()).thenReturn(true);
        when(sessionDTO.getUserId()).thenReturn(userId);
        when(restaurantGateway.getOneById(restaurantId)).thenReturn(restaurant);
        when(storageGateway.uploadFile(fileDTO)).thenReturn(fileUrl);
        when(productGateway.create(any(ProductDomain.class))).thenAnswer(invocation -> {
            ProductDomain product = invocation.getArgument(0);
            product.setId(1L);
            return product;
        });

        ProductDomain createdProduct = productCreateUseCase.execute(productToCreate);

        assertNotNull(createdProduct);
        assertEquals(1L, createdProduct.getId());
        assertEquals(fileUrl, createdProduct.getPhotoUrl());
        verify(productGateway).create(productToCreate);
    }

    @Test
    void execute_deveLancarExcecao_quandoUsuarioNaoForAutorizado() {
        Long restaurantId = 1L;
        Long ownerId = 10L;
        Long requestUserId = 20L;

        UserDomain owner = UserDomain.builder().id(ownerId).build();
        RestaurantDomain restaurant = RestaurantDomain.builder().id(restaurantId).user(owner).build();
        ProductDomain productToCreate = ProductDomain.builder()
                .restaurant(RestaurantDomain.builder().id(restaurantId).build())
                .build();

        when(sessionGateway.getSessionDTO()).thenReturn(sessionDTO);
        when(sessionDTO.isNotAdmin()).thenReturn(true);
        when(sessionDTO.getUserId()).thenReturn(requestUserId);
        when(restaurantGateway.getOneById(restaurantId)).thenReturn(restaurant);

        assertThrows(ApplicationException.class, () -> productCreateUseCase.execute(productToCreate));

        verify(storageGateway, never()).uploadFile(any());
        verify(productGateway, never()).create(any());
    }
}