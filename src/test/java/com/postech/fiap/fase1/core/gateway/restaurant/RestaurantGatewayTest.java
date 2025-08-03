package com.postech.fiap.fase1.core.gateway.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantRequestDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.presenter.RestaurantPresenter;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.Assertions;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantGatewayTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private RestaurantPresenter restaurantPresenter;

    @InjectMocks
    private RestaurantGateway restaurantGateway;

    private RestaurantDomain restaurantDomain;
    private RestaurantDTO restaurantDTO;

    @BeforeEach
    void setUp() {
        restaurantDomain = RestaurantDomain.builder()
                .id(1L)
                .name("Restaurante Teste")
                .cnpj("12345678000199")
                .build();

        restaurantDTO = RestaurantDTO.builder()
                .id(1L)
                .name("Restaurante Teste")
                .cnpj("12345678000199")
                .build();
    }

    @Test
    void create_shouldReturnANewRestaurantGateway() {
        var newRestaurant = RestaurantGateway.build(dataSource);

        Assertions.assertNotNull(newRestaurant);
    }

    @Test
    @DisplayName("Deve criar um restaurante com sucesso")
    void create_shouldReturnCreatedRestaurantDomain() {
        // Arrange
        when(restaurantPresenter.toDTO(restaurantDomain)).thenReturn(restaurantDTO);
        when(dataSource.createRestaurant(restaurantDTO)).thenReturn(restaurantDTO);
        when(restaurantPresenter.toDomain(restaurantDTO)).thenReturn(restaurantDomain);

        // Act
        RestaurantDomain result = restaurantGateway.create(restaurantDomain);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Restaurante Teste");
        verify(restaurantPresenter).toDTO(restaurantDomain);
        verify(dataSource).createRestaurant(restaurantDTO);
        verify(restaurantPresenter).toDomain(restaurantDTO);
    }

    @Test
    @DisplayName("Deve atualizar um restaurante com sucesso")
    void update_shouldReturnUpdatedRestaurantDomain() {
        // Arrange
        when(restaurantPresenter.toDTO(restaurantDomain)).thenReturn(restaurantDTO);
        when(dataSource.updateRestaurant(restaurantDTO)).thenReturn(restaurantDTO);
        when(restaurantPresenter.toDomain(restaurantDTO)).thenReturn(restaurantDomain);

        // Act
        RestaurantDomain result = restaurantGateway.update(restaurantDomain);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(restaurantPresenter).toDTO(restaurantDomain);
        verify(dataSource).updateRestaurant(restaurantDTO);
        verify(restaurantPresenter).toDomain(restaurantDTO);
    }

    @Test
    @DisplayName("Deve verificar se existe restaurante com mesmo CNPJ")
    void hasRestaurantWithCNPJ_shouldDelegateToDataSource() {
        // Arrange
        when(dataSource.hasRestaurantWithCNPJ(1L, "12345678000199")).thenReturn(true);

        // Act
        boolean result = restaurantGateway.hasRestaurantWithCNPJ(1L, "12345678000199");

        // Assert
        assertThat(result).isTrue();
        verify(dataSource).hasRestaurantWithCNPJ(1L, "12345678000199");
    }

    @Test
    @DisplayName("Deve retornar Optional de RestaurantDomain quando restaurante existe")
    void getById_whenRestaurantExists_shouldReturnOptionalOfDomain() {
        // Arrange
        when(dataSource.getRestaurantById(1L)).thenReturn(Optional.of(restaurantDTO));
        when(restaurantPresenter.toDomain(restaurantDTO)).thenReturn(restaurantDomain);

        // Act
        Optional<RestaurantDomain> result = restaurantGateway.getById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        verify(dataSource).getRestaurantById(1L);
        verify(restaurantPresenter).toDomain(restaurantDTO);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando restaurante não existe")
    void getById_whenRestaurantDoesNotExist_shouldReturnEmptyOptional() {
        // Arrange
        when(dataSource.getRestaurantById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<RestaurantDomain> result = restaurantGateway.getById(99L);

        // Assert
        assertThat(result).isNotPresent();
        verify(dataSource).getRestaurantById(99L);
        verify(restaurantPresenter, never()).toDomain(any(RestaurantRequestDTO.class));
    }

    @Test
    @DisplayName("Deve retornar RestaurantDomain quando restaurante existe")
    void getOneById_whenRestaurantExists_shouldReturnDomain() {
        // Arrange
        when(dataSource.getRestaurantById(1L)).thenReturn(Optional.of(restaurantDTO));
        when(restaurantPresenter.toDomain(restaurantDTO)).thenReturn(restaurantDomain);

        // Act
        RestaurantDomain result = restaurantGateway.getOneById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Deve lançar ApplicationException quando restaurante não existe")
    void getOneById_whenRestaurantDoesNotExist_shouldThrowException() {
        // Arrange
        when(dataSource.getRestaurantById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> restaurantGateway.getOneById(99L))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("Restaurant Not found");
    }

    @Test
    @DisplayName("Deve retornar uma página de restaurantes")
    void getAllPaged_shouldReturnPageOfRestaurantDomain() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<RestaurantDTO> restaurantDTOPage = new PageImpl<>(List.of(restaurantDTO), pageable, 1);

        when(dataSource.getAllRestaurantPaged(pageable)).thenReturn(restaurantDTOPage);
        when(restaurantPresenter.toDomain(restaurantDTO)).thenReturn(restaurantDomain);

        // Act
        Page<RestaurantDomain> result = restaurantGateway.getAllPaged(pageable);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getCnpj()).isEqualTo("12345678000199");
        verify(dataSource).getAllRestaurantPaged(pageable);
        verify(restaurantPresenter).toDomain(restaurantDTO);
    }

    @Test
    @DisplayName("Deve chamar o método de deleção do data source")
    void deleteById_shouldCallDataSourceDelete() {
        // Arrange
        Long restaurantId = 1L;
        doNothing().when(dataSource).deleteRestaurantById(restaurantId);

        // Act
        restaurantGateway.deleteById(restaurantId);

        // Assert
        verify(dataSource).deleteRestaurantById(restaurantId);
        verifyNoMoreInteractions(dataSource); // Garante que nada mais foi chamado
    }
}