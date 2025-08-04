package com.postech.fiap.fase1.core.domain.validation.session;

import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessionUserAllowedValidatorTest {
    private ISessionGateway sessionGateway;
    private SessionUserAllowedValidator validator;
    private SessionDTO sessionDTO;

    @BeforeEach
    void setUp() {
        sessionGateway = mock(ISessionGateway.class);
        sessionDTO = mock(SessionDTO.class);
        when(sessionGateway.getSessionDTO()).thenReturn(sessionDTO);
        validator = new SessionUserAllowedValidator(sessionGateway);
    }

    @Test
    void validate_shouldAllowAdminUser() {
        when(sessionDTO.isNotAdmin()).thenReturn(false);
        assertDoesNotThrow(() -> validator.validate(123L));
    }

    @Test
    void validate_shouldAllowSameUserId() {
        when(sessionDTO.isNotAdmin()).thenReturn(true);
        when(sessionDTO.getUserId()).thenReturn(123L);
        assertDoesNotThrow(() -> validator.validate(123L));
    }

    @Test
    void validate_shouldThrowWhenNotAdminAndDifferentUserId() {
        when(sessionDTO.isNotAdmin()).thenReturn(true);
        when(sessionDTO.getUserId()).thenReturn(456L);
        ApplicationException ex = assertThrows(ApplicationException.class, () -> validator.validate(123L));
        assertEquals("User not authorized to perform this action", ex.getMessage());
    }

    @Test
    void validate_noArg_shouldCallValidateWithNull() {
        when(sessionDTO.isNotAdmin()).thenReturn(false);
        assertDoesNotThrow(() -> validator.validate());
        verify(sessionGateway, times(1)).getSessionDTO();
    }
}