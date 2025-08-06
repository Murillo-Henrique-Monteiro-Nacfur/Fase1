package com.postech.fiap.fase1.core.domain.usecase.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.restaurant.IRestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
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
class RestaurantCreateUseCaseTest {

    @Mock
    private IRestaurantGateway restaurantGateway;
    @Mock
    private IUserGateway userGateway;
    @Mock
    private ISessionGateway sessionGateway;

    private RestaurantCreateUseCase useCase;
    private RestaurantDomain restaurantDomain;
    private UserDomain userDomain;

    @BeforeEach
    void setUp() {
        useCase = new RestaurantCreateUseCase(restaurantGateway, userGateway, sessionGateway);
        userDomain = UserDomain.builder().id(10L).name("Usuário Teste").build();
        restaurantDomain = RestaurantDomain.builder().user(UserDomain.builder().id(10L).build()).name("Restaurante Teste").cnpj("12345678901234").openTime(java.time.LocalTime.of(8, 0)).closeTime(java.time.LocalTime.of(18, 0)).build();
    }

    @Test
    @DisplayName("Deve criar restaurante com sucesso")
    void execute_shouldCreateRestaurantSuccessfully() {
        when(userGateway.getUserById(10L)).thenReturn(userDomain);
        when(restaurantGateway.hasRestaurantWithCNPJ(null, "12345678901234")).thenReturn(false);
        when(sessionGateway.getSessionDTO()).thenReturn(com.postech.fiap.fase1.core.dto.auth.SessionDTO.builder().userId(10L).userRole("ADMIN").build());
        when(restaurantGateway.create(any(RestaurantDomain.class))).thenReturn(restaurantDomain);

        RestaurantDomain result = useCase.execute(restaurantDomain);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Restaurante Teste");
        verify(restaurantGateway).create(any(RestaurantDomain.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se CNPJ já estiver em uso")
    void execute_shouldThrowExceptionIfCNPJUsed() {
        lenient().when(userGateway.getUserById(10L)).thenReturn(userDomain);
        lenient().when(restaurantGateway.hasRestaurantWithCNPJ(null, "12345678901234")).thenReturn(true);
        lenient().when(sessionGateway.getSessionDTO()).thenReturn(com.postech.fiap.fase1.core.dto.auth.SessionDTO.builder().userId(10L).userRole("ADMIN").build());

        assertThatThrownBy(() -> useCase.execute(restaurantDomain))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("CNPJ");
    }

    @Test
    @DisplayName("Deve lançar exceção se usuário não autorizado")
    void execute_shouldThrowExceptionIfUserNotAllowed() {
        when(userGateway.getUserById(10L)).thenReturn(userDomain);
        when(restaurantGateway.hasRestaurantWithCNPJ(null, "12345678901234")).thenReturn(false);
        when(sessionGateway.getSessionDTO()).thenReturn(com.postech.fiap.fase1.core.dto.auth.SessionDTO.builder().userId(99L).userRole("USER").build());

        assertThatThrownBy(() -> useCase.execute(restaurantDomain))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("Operation not allowed");
        verify(restaurantGateway, never()).create(any(RestaurantDomain.class));
    }
}