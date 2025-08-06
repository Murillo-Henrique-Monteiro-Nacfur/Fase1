package com.postech.fiap.fase1.core.domain.validation.restaurant.implementation;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantOpenCloseTimeValidationTest {
    private RestaurantOpenCloseTimeValidation validator;
    private RestaurantDomain restaurantDomain;

    @BeforeEach
    void setUp() {
        validator = new RestaurantOpenCloseTimeValidation();
        restaurantDomain = mock(RestaurantDomain.class);
    }

    @Test
    void validate_shouldThrowWhenOpenCloseTimeInvalid() {
        when(restaurantDomain.isOpenAndCloseTimeInvalids()).thenReturn(true);
        ApplicationException ex = assertThrows(ApplicationException.class, () -> validator.validate(restaurantDomain));
        assertEquals("Open and close time are invalids", ex.getMessage());
    }

    @Test
    void validate_shouldNotThrowWhenOpenCloseTimeValid() {
        when(restaurantDomain.isOpenAndCloseTimeInvalids()).thenReturn(false);
        assertDoesNotThrow(() -> validator.validate(restaurantDomain));
    }
}