package com.postech.fiap.fase1.core.controllers.user;

import com.postech.fiap.fase1.core.domain.usecase.user.UserDeleteUseCase;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDeleteCoreControllerTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private SessionSource sessionSource;
    @Mock
    private UserDeleteUseCase userDeleteUseCase;

    @InjectMocks
    private UserDeleteCoreController userDeleteCoreController;

    @BeforeEach
    void setUp() throws Exception {
        userDeleteCoreController = Mockito.spy(new UserDeleteCoreController(dataSource, sessionSource));
        var field = UserDeleteCoreController.class.getDeclaredField("userDeleteUseCase");
        field.setAccessible(true);
        field.set(userDeleteCoreController, userDeleteUseCase);
    }

    @Test
    @DisplayName("Deve chamar o use case para deletar usuário com sucesso")
    void delete_shouldCallUseCase() {
        Long idUser = 1L;
        doNothing().when(userDeleteUseCase).execute(idUser);
        userDeleteCoreController.delete(idUser);
        verify(userDeleteUseCase, times(1)).execute(idUser);
    }

    @Test
    @DisplayName("Deve propagar exceção do use case ao deletar usuário")
    void delete_shouldPropagateException() {
        Long idUser = 2L;
        doThrow(new RuntimeException("Erro ao deletar")).when(userDeleteUseCase).execute(idUser);
        assertThatThrownBy(() -> userDeleteCoreController.delete(idUser))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Erro ao deletar");
    }
}