package com.postech.fiap.fase1.core.domain.validation.restaurant.implementation;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantUserAllowedValidationTest {
    private RestaurantUserAllowedValidation validator;
    private SessionDTO sessionDTO;
    private RestaurantDomain restaurantDomain;

    @BeforeEach
    void setUp() {
        ISessionGateway sessionGateway = mock(ISessionGateway.class);
        sessionDTO = mock(SessionDTO.class);
        restaurantDomain = mock(RestaurantDomain.class);
        when(sessionGateway.getSessionDTO()).thenReturn(sessionDTO);
        validator = new RestaurantUserAllowedValidation(sessionGateway);
    }

    @Test
    void validate_shouldAllowAdminUser() {
        when(sessionDTO.isNotAdmin()).thenReturn(false);
        var user = mock(UserDomain.class);
        when(restaurantDomain.getUser()).thenReturn(user);
        assertDoesNotThrow(() -> validator.validate(restaurantDomain));
    }

    @Test
    void validate_shouldAllowOwnerUser() {
        when(sessionDTO.isNotAdmin()).thenReturn(false);
        when(sessionDTO.getUserId()).thenReturn(123L);
        var user = mock(UserDomain.class);
        when(user.getId()).thenReturn(123L);
        when(restaurantDomain.getUser()).thenReturn(user);
        assertDoesNotThrow(() -> validator.validate(restaurantDomain));
    }

    @Test
    void validate_shouldThrowWhenNotAdminAndNotOwner() {
        when(sessionDTO.isNotAdmin()).thenReturn(true);
        when(sessionDTO.getUserId()).thenReturn(456L);
        var user = mock(UserDomain.class);
        when(restaurantDomain.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(123L);
        ApplicationException ex = assertThrows(ApplicationException.class, () -> validator.validate(restaurantDomain));
        assertEquals("Operation not allowed", ex.getMessage());
    }
}