package com.postech.fiap.fase1.core.domain.usecase.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.restaurant.IRestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUpdateUseCaseTest {

    @Mock
    private IRestaurantGateway restaurantGateway;
    @Mock
    private ISessionGateway sessionGateway;
    private RestaurantUpdateUseCase useCase;
    private RestaurantDomain restaurantDomain;
    private RestaurantDomain restaurantDomainOld;

    @BeforeEach
    void setUp() {
        useCase = new RestaurantUpdateUseCase(restaurantGateway, sessionGateway);
        java.time.LocalTime openTime = java.time.LocalTime.of(8, 0);
        java.time.LocalTime closeTime = java.time.LocalTime.of(18, 0);
        restaurantDomain = RestaurantDomain.builder().id(1L).name("Novo Restaurante").openTime(openTime).closeTime(closeTime).build();
        UserDomain userDomain = UserDomain.builder().id(10L).name("Usuário Teste").build();
        restaurantDomainOld = RestaurantDomain.builder().id(1L).name("Antigo Restaurante").user(userDomain).openTime(openTime).closeTime(closeTime).build();
        when(sessionGateway.getSessionDTO()).thenReturn(com.postech.fiap.fase1.core.dto.auth.SessionDTO.builder().userId(10L).userRole("ADMIN").build());
    }

    @Test
    @DisplayName("Deve atualizar restaurante com sucesso")
    void execute_shouldUpdateRestaurantSuccessfully() {
        when(restaurantGateway.getOneById(1L)).thenReturn(restaurantDomainOld);
        when(restaurantGateway.update(any(RestaurantDomain.class))).thenReturn(restaurantDomain);

        RestaurantDomain result = useCase.execute(restaurantDomain);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Novo Restaurante");
        verify(restaurantGateway).update(restaurantDomain);
    }

    @Test
    @DisplayName("Deve lançar exceção se validação falhar")
    void execute_shouldThrowExceptionIfValidationFails() {
        when(restaurantGateway.getOneById(1L)).thenReturn(restaurantDomainOld);
        when(sessionGateway.getSessionDTO()).thenReturn(com.postech.fiap.fase1.core.dto.auth.SessionDTO.builder().userId(99L).userRole("USER").build());

        assertThatThrownBy(() -> useCase.execute(restaurantDomain))
                .isInstanceOf(com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException.class)
                .hasMessageContaining("Operation not allowed");
        verify(restaurantGateway, never()).update(any());
    }
}