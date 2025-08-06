package com.postech.fiap.fase1.core.domain.validation.product.implementation;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.validation.session.ISessionValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RestaurantOwnerVerificationTest {
    private ISessionValidation sessionValidation;
    private RestaurantOwnerVerification verification;

    @BeforeEach
    void setUp() {
        sessionValidation = mock(ISessionValidation.class);
        verification = new RestaurantOwnerVerification(sessionValidation);
    }

    @Test
    void validation_shouldCallSessionValidationWithOwnerId() {
        Long ownerId = 123L;
        RestaurantDomain restaurant = mock(RestaurantDomain.class);
        when(restaurant.getIdUserOwner()).thenReturn(ownerId);
        ProductDomain product = mock(ProductDomain.class);
        when(product.getRestaurant()).thenReturn(restaurant);

        verification.validation(product);

        verify(sessionValidation, times(1)).validate(ownerId);
    }

    @Test
    void validation_shouldPropagateExceptionFromSessionValidation() {
        Long ownerId = 456L;
        RestaurantDomain restaurant = mock(RestaurantDomain.class);
        when(restaurant.getIdUserOwner()).thenReturn(ownerId);
        ProductDomain product = mock(ProductDomain.class);
        when(product.getRestaurant()).thenReturn(restaurant);
        doThrow(new RuntimeException("Session validation failed")).when(sessionValidation).validate(ownerId);

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> verification.validation(product));
    }
}