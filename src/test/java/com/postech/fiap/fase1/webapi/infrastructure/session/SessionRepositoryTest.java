package com.postech.fiap.fase1.webapi.infrastructure.session;

import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SessionRepositoryTest {

    @InjectMocks
    private SessionRepository sessionRepository;

    @Test
    @DisplayName("Deve retornar o SessionDTO fornecido pelo ThreadLocalStorage")
    void shouldReturnSessionDTOFromThreadLocal() {
        SessionDTO expectedSessionDTO = SessionDTO.builder()
                .userId(123L)
                .userLogin("testuser")
                .userRole("ADMIN")
                .build();

        try (MockedStatic<ThreadLocalStorage> mockedStatic = Mockito.mockStatic(ThreadLocalStorage.class)) {
            mockedStatic.when(ThreadLocalStorage::getSession).thenReturn(expectedSessionDTO);

            SessionDTO actualSessionDTO = sessionRepository.getSessionDTO();

            assertThat(actualSessionDTO)
                    .isNotNull()
                    .isEqualTo(expectedSessionDTO);
        }
    }

    @Test
    @DisplayName("Deve retornar um SessionDTO vazio se o ThreadLocalStorage não tiver sessão")
    void shouldReturnEmptySessionDTOWhenThreadLocalIsEmpty() {
        SessionDTO emptySessionDTO = new SessionDTO();

        try (MockedStatic<ThreadLocalStorage> mockedStatic = Mockito.mockStatic(ThreadLocalStorage.class)) {
            mockedStatic.when(ThreadLocalStorage::getSession).thenReturn(emptySessionDTO);

            SessionDTO actualSessionDTO = sessionRepository.getSessionDTO();

            assertThat(actualSessionDTO).isNotNull();
            assertThat(actualSessionDTO.getUserId()).isNull();
            assertThat(actualSessionDTO.getUserLogin()).isNull();
        }
    }
}