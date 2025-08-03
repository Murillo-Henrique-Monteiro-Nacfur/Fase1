package com.postech.fiap.fase1.core.gateway.session;

import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionGatewayTest {

    @Mock
    private SessionSource sessionSource;

    private SessionGateway sessionGateway;

    @BeforeEach
    void setUp() {
        sessionGateway = SessionGateway.build(sessionSource);
    }

    @Test
    @DisplayName("Deve retornar SessionDTO do SessionSource com sucesso")
    void getSessionDTO_shouldReturnSessionDTOFromSource() {
        SessionDTO expectedSessionDTO = SessionDTO.builder()
                .userName("test-user").build();

        when(sessionSource.getSessionDTO()).thenReturn(expectedSessionDTO);

        SessionDTO actualSessionDTO = sessionGateway.getSessionDTO();

        verify(sessionSource).getSessionDTO();

        assertThat(actualSessionDTO)
                .isNotNull()
                .isEqualTo(expectedSessionDTO);

        assertThat(actualSessionDTO.getUserName()).isEqualTo(expectedSessionDTO.getUserName());
    }
}