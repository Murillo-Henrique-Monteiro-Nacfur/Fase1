package com.postech.fiap.fase1.core.dto.auth;

import com.postech.fiap.fase1.webapi.data.entity.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionDTOTest {

    @Test
    void testConstructorWithUserTokenBodyDTO() {
        UserTokenBodyDTO userTokenBodyDTO = UserTokenBodyDTO.builder()
                .id(1L)
                .name("Test User")
                .login("testuser")
                .email("test@example.com")
                .role(Role.ADMIN.name())
                .build();

        SessionDTO sessionDTO = new SessionDTO(userTokenBodyDTO);

        assertEquals(1L, sessionDTO.getUserId());
        assertEquals("Test User", sessionDTO.getUserName());
        assertEquals("testuser", sessionDTO.getUserLogin());
        assertEquals("test@example.com", sessionDTO.getUserEmail());
        assertEquals(Role.ADMIN.name(), sessionDTO.getUserRole());
    }

    @Test
    void testIsAdmin_whenRoleIsAdmin() {
        SessionDTO sessionDTO = SessionDTO.builder().userRole(Role.ADMIN.name()).build();
        assertTrue(sessionDTO.isAdmin());
    }

    @Test
    void testIsAdmin_whenRoleIsNotAdmin() {
        SessionDTO sessionDTO = SessionDTO.builder().userRole(Role.CLIENT.name()).build();
        assertFalse(sessionDTO.isAdmin());
    }

    @Test
    void testIsNotAdmin_whenRoleIsNotAdmin() {
        SessionDTO sessionDTO = SessionDTO.builder().userRole(Role.CLIENT.name()).build();
        assertTrue(sessionDTO.isNotAdmin());
    }

    @Test
    void testIsNotAdmin_whenRoleIsAdmin() {
        SessionDTO sessionDTO = SessionDTO.builder().userRole(Role.ADMIN.name()).build();
        assertFalse(sessionDTO.isNotAdmin());
    }

    @Test
    void testNoArgsConstructor() {
        SessionDTO sessionDTO = new SessionDTO();
        assertNull(sessionDTO.getUserId());
        assertNull(sessionDTO.getUserName());
        assertNull(sessionDTO.getUserLogin());
        assertNull(sessionDTO.getUserEmail());
        assertNull(sessionDTO.getUserRole());
    }

    @Test
    void testAllArgsConstructorAndGetters() {
        SessionDTO sessionDTO = new SessionDTO(1L, "Test User", "testuser", "test@example.com", Role.CLIENT.name());

        assertEquals(1L, sessionDTO.getUserId());
        assertEquals("Test User", sessionDTO.getUserName());
        assertEquals("testuser", sessionDTO.getUserLogin());
        assertEquals("test@example.com", sessionDTO.getUserEmail());
        assertEquals(Role.CLIENT.name(), sessionDTO.getUserRole());
    }

    @Test
    void testBuilder() {
        SessionDTO sessionDTO = SessionDTO.builder()
                .userId(1L)
                .userName("Test User")
                .userLogin("testuser")
                .userEmail("test@example.com")
                .userRole(Role.ADMIN.name())
                .build();

        assertEquals(1L, sessionDTO.getUserId());
        assertEquals("Test User", sessionDTO.getUserName());
        assertEquals("testuser", sessionDTO.getUserLogin());
        assertEquals("test@example.com", sessionDTO.getUserEmail());
        assertEquals(Role.ADMIN.name(), sessionDTO.getUserRole());
    }
}