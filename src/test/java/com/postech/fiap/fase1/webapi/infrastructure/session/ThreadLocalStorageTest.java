package com.postech.fiap.fase1.webapi.infrastructure.session;

import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.dto.auth.UserTokenBodyDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ThreadLocalStorageTest {

    /**
     * É uma prática crucial de higiene limpar o ThreadLocal após cada teste.
     * Isso evita que o estado de um teste "vaze" e interfira no próximo,
     * garantindo que cada teste comece com um ambiente limpo.
     */
    @AfterEach
    void tearDown() {
        ThreadLocalStorage.clear();
    }

    @Test
    @DisplayName("Deve construir e obter a sessão corretamente para o thread atual")
    void buildAndGetSession_shouldStoreAndRetrieveDataCorrectly() {
        // Arrange
        UserTokenBodyDTO userToken = UserTokenBodyDTO.builder()
                .id(1L)
                .name("Test User")
                .login("testuser")
                .email("test@example.com")
                .role("CLIENT")
                .build();

        // Act
        // 1. Armazena os dados do token no ThreadLocal.
        ThreadLocalStorage.build(userToken);
        // 2. Recupera os dados como um SessionDTO.
        SessionDTO session = ThreadLocalStorage.getSession();

        // Assert
        // 3. Verifica se o SessionDTO foi populado corretamente.
        assertThat(session).isNotNull();
        assertThat(session.getUserId()).isEqualTo(1L);
        assertThat(session.getUserName()).isEqualTo("Test User");
        assertThat(session.getUserLogin()).isEqualTo("testuser");
        assertThat(session.getUserEmail()).isEqualTo("test@example.com");
        assertThat(session.getUserRole()).isEqualTo("CLIENT");
    }

    @Test
    @DisplayName("Deve retornar um SessionDTO vazio quando nenhuma sessão foi construída")
    void getSession_whenNoSessionIsBuilt_shouldReturnEmptyDTO() {
        // Arrange (Nenhuma ação de preparação é necessária)

        // Act
        SessionDTO session = ThreadLocalStorage.getSession();

        // Assert
        // Verifica se um objeto não nulo é retornado, mas com todos os campos nulos,
        // conforme a lógica de `new SessionDTO()`.
        assertThat(session).isNotNull();
        assertThat(session.getUserId()).isNull();
        assertThat(session.getUserName()).isNull();
        assertThat(session.getUserLogin()).isNull();
        assertThat(session.getUserEmail()).isNull();
        assertThat(session.getUserRole()).isNull();
    }

    @Test
    @DisplayName("Deve limpar a sessão do thread atual após chamar clear")
    void clear_shouldRemoveSessionFromCurrentThread() {
        // Arrange
        UserTokenBodyDTO userToken = UserTokenBodyDTO.builder().id(1L).login("user-to-clear").build();
        ThreadLocalStorage.build(userToken);

        // Verificação de sanidade: garante que a sessão foi definida antes de limpar.
        SessionDTO sessionBeforeClear = ThreadLocalStorage.getSession();
        assertThat(sessionBeforeClear.getUserId()).isEqualTo(1L);

        // Act
        ThreadLocalStorage.clear();
        SessionDTO sessionAfterClear = ThreadLocalStorage.getSession();

        // Assert
        // Verifica se, após limpar, a sessão retornada está vazia.
        assertThat(sessionAfterClear).isNotNull();
        assertThat(sessionAfterClear.getUserId()).isNull();
        assertThat(sessionAfterClear.getUserLogin()).isNull();
    }

    @Test
    @DisplayName("Deve manter sessões separadas para threads diferentes")
    void build_shouldBeThreadSafe() throws InterruptedException {
        // Arrange
        UserTokenBodyDTO user1 = UserTokenBodyDTO.builder().id(1L).login("user1").build();
        UserTokenBodyDTO user2 = UserTokenBodyDTO.builder().id(2L).login("user2").build();

        // Act & Assert
        // Cria e inicia a primeira thread, que define e verifica sua própria sessão.
        Thread thread1 = new Thread(() -> {
            ThreadLocalStorage.build(user1);
            SessionDTO session1 = ThreadLocalStorage.getSession();
            assertThat(session1.getUserId()).isEqualTo(1L);
            assertThat(session1.getUserLogin()).isEqualTo("user1");
        });

        // Cria e inicia a segunda thread, que faz o mesmo com dados diferentes.
        Thread thread2 = new Thread(() -> {
            ThreadLocalStorage.build(user2);
            SessionDTO session2 = ThreadLocalStorage.getSession();
            assertThat(session2.getUserId()).isEqualTo(2L);
            assertThat(session2.getUserLogin()).isEqualTo("user2");
        });

        thread1.start();
        thread2.start();

        // Aguarda a conclusão de ambas as threads para finalizar o teste.
        thread1.join();
        thread2.join();
    }
}