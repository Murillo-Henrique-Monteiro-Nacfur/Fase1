package com.postech.fiap.fase1.core.domain.usecase.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.gateway.restaurant.IRestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantDeleteUseCaseTest {

    @Mock
    private IRestaurantGateway restaurantGateway;
    @Mock
    private ISessionGateway sessionGateway;

    private RestaurantDeleteUseCase useCase;
    private RestaurantDomain restaurantDomain;

    @BeforeEach
    void setUp() {
        useCase = new RestaurantDeleteUseCase(restaurantGateway, sessionGateway);
        restaurantDomain = RestaurantDomain.builder().id(1L).name("Restaurante Teste").user(
                com.postech.fiap.fase1.core.domain.model.UserDomain.builder().id(10L).name("Usuário Teste").build()
        ).openTime(java.time.LocalTime.of(8, 0)).closeTime(java.time.LocalTime.of(18, 0)).build();
    }

    @Test
    @DisplayName("Deve deletar restaurante com sucesso")
    void execute_shouldDeleteRestaurantSuccessfully() {
        when(restaurantGateway.getOneById(1L)).thenReturn(restaurantDomain);
        when(sessionGateway.getSessionDTO()).thenReturn(com.postech.fiap.fase1.core.dto.auth.SessionDTO.builder().userId(10L).userRole("ADMIN").build());

        useCase.execute(1L);

        verify(restaurantGateway).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção se usuário não autorizado")
    void execute_shouldThrowExceptionIfUserNotAllowed() {
        when(restaurantGateway.getOneById(1L)).thenReturn(restaurantDomain);
        when(sessionGateway.getSessionDTO()).thenReturn(com.postech.fiap.fase1.core.dto.auth.SessionDTO.builder().userId(99L).userRole("USER").build());

        assertThatThrownBy(() -> useCase.execute(1L))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("Operation not allowed");
        verify(restaurantGateway, never()).deleteById(anyLong());
    }
}