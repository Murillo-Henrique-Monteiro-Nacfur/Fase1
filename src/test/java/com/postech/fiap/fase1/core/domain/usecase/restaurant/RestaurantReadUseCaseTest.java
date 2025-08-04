package com.postech.fiap.fase1.core.domain.usecase.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.gateway.restaurant.IRestaurantGateway;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantReadUseCaseTest {

    @Mock
    private IRestaurantGateway restaurantGateway;

    @InjectMocks
    private RestaurantReadUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new RestaurantReadUseCase(restaurantGateway);
    }

    @Test
    @DisplayName("Deve retornar restaurante por ID com sucesso")
    void getById_shouldReturnRestaurant() {
        RestaurantDomain restaurant = RestaurantDomain.builder().id(1L).name("Restaurante Teste").build();
        when(restaurantGateway.getById(1L)).thenReturn(Optional.of(restaurant));

        RestaurantDomain result = useCase.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Restaurante Teste");
        verify(restaurantGateway).getById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção se restaurante não encontrado")
    void getById_shouldThrowExceptionIfNotFound() {
        when(restaurantGateway.getById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.getById(2L))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("Restaurant not found");
        verify(restaurantGateway).getById(2L);
    }

    @Test
    @DisplayName("Deve retornar página de restaurantes")
    void getAllPaged_shouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 2);
        RestaurantDomain r1 = RestaurantDomain.builder().id(1L).name("R1").build();
        RestaurantDomain r2 = RestaurantDomain.builder().id(2L).name("R2").build();
        Page<RestaurantDomain> page = new PageImpl<>(List.of(r1, r2), pageable, 2);
        when(restaurantGateway.getAllPaged(pageable)).thenReturn(page);

        Page<RestaurantDomain> result = useCase.getAllPaged(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getName()).isEqualTo("R1");
        assertThat(result.getContent().get(1).getName()).isEqualTo("R2");
        verify(restaurantGateway).getAllPaged(pageable);
    }
}