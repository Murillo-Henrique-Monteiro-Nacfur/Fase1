package com.postech.fiap.fase1.core.domain.validation.restaurant.implementation;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.gateway.restaurant.IRestaurantGateway;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantCNPJUsedValidationTest {
    private IRestaurantGateway restaurantGateway;
    private RestaurantCNPJUsedValidation validator;
    private RestaurantDomain restaurantDomain;

    @BeforeEach
    void setUp() {
        restaurantGateway = mock(IRestaurantGateway.class);
        restaurantDomain = mock(RestaurantDomain.class);
        validator = new RestaurantCNPJUsedValidation(restaurantGateway);
    }

    @Test
    void validate_shouldThrowWhenCNPJAlreadyUsed() {
        when(restaurantDomain.getId()).thenReturn(1L);
        when(restaurantDomain.getCnpj()).thenReturn("12345678901234");
        when(restaurantGateway.hasRestaurantWithCNPJ(1L, "12345678901234")).thenReturn(true);
        ApplicationException ex = assertThrows(ApplicationException.class, () -> validator.validate(restaurantDomain));
        assertEquals("Already Has a restaurant with this CNPJ", ex.getMessage());
    }

    @Test
    void validate_shouldNotThrowWhenCNPJNotUsed() {
        when(restaurantDomain.getId()).thenReturn(2L);
        when(restaurantDomain.getCnpj()).thenReturn("98765432109876");
        when(restaurantGateway.hasRestaurantWithCNPJ(2L, "98765432109876")).thenReturn(false);
        assertDoesNotThrow(() -> validator.validate(restaurantDomain));
    }
}