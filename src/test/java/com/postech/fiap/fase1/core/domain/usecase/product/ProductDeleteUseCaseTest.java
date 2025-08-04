package com.postech.fiap.fase1.core.domain.usecase.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.gateway.product.IProductGateway;
import com.postech.fiap.fase1.core.gateway.restaurant.IRestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductDeleteUseCaseTest {

    @Mock
    private IProductGateway productGateway;

    @Mock
    private SessionGateway sessionGateway;

    @Mock
    private IRestaurantGateway restaurantGateway;

    @InjectMocks
    private ProductDeleteUseCase productDeleteUseCase;

    @Mock
    private SessionDTO sessionDTO;

    @BeforeEach
    void setUp() {
        when(sessionGateway.getSessionDTO()).thenReturn(sessionDTO);
    }

    @Test
    void delete_Product_Admin() {
        Long productId = 1L;
        ProductDomain productDomain = ProductDomain.builder().build();
        RestaurantDomain restaurantDomain = RestaurantDomain.builder().build();
        productDomain.setRestaurant(restaurantDomain);

        when(productGateway.getProductById(productId)).thenReturn(productDomain);
        when(restaurantGateway.getOneById(any())).thenReturn(restaurantDomain);
        when(sessionDTO.isNotAdmin()).thenReturn(false);

        assertDoesNotThrow(() -> productDeleteUseCase.delete(productId));

        verify(productGateway).delete(productDomain);
    }

    @Test
    void delete_Product_RestaurantOwner() {
        Long productId = 1L;
        Long userId = 10L;
        ProductDomain productDomain = ProductDomain.builder().build();
        RestaurantDomain restaurantDomain = RestaurantDomain.builder().build();
        UserDomain owner = UserDomain.builder().build();
        owner.setId(userId);
        restaurantDomain.setUser(owner);
        productDomain.setRestaurant(restaurantDomain);

        when(productGateway.getProductById(productId)).thenReturn(productDomain);
        when(restaurantGateway.getOneById(any())).thenReturn(restaurantDomain);
        when(sessionDTO.isNotAdmin()).thenReturn(true);
        when(sessionDTO.getUserId()).thenReturn(userId);

        assertDoesNotThrow(() -> productDeleteUseCase.delete(productId));

        verify(productGateway).delete(productDomain);
    }

    @Test
    void delete_Exception_UserUnauthorized() {
        Long productId = 1L;
        Long ownerId = 10L;
        Long requestUserId = 20L;

        ProductDomain productDomain = ProductDomain.builder().build();
        RestaurantDomain restaurantDomain = RestaurantDomain.builder().build();
        UserDomain owner = UserDomain.builder().build();
        owner.setId(ownerId);
        restaurantDomain.setUser(owner);
        productDomain.setRestaurant(restaurantDomain);

        when(productGateway.getProductById(productId)).thenReturn(productDomain);
        when(restaurantGateway.getOneById(any())).thenReturn(restaurantDomain);
        when(sessionDTO.isNotAdmin()).thenReturn(true);
        when(sessionDTO.getUserId()).thenReturn(requestUserId);

        assertThrows(ApplicationException.class, () -> productDeleteUseCase.delete(productId));

        verify(productGateway, never()).delete(any());
    }
}