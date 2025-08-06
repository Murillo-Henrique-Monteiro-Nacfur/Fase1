package com.postech.fiap.fase1.core.gateway.address;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.domain.model.AddressableDomain;
import com.postech.fiap.fase1.core.dto.address.AddressDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.presenter.AddressPresenter;
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
class AddressGatewayTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private AddressPresenter addressPresenter;

    @InjectMocks
    private AddressGateway addressGateway;

    private AddressDomain addressDomain;
    private AddressDTO addressDTO;
    private UserDTO userDTO;
    private RestaurantDTO restaurantDTO;

    @BeforeEach
    void setUp() {
        // Mock para o "dono" do endereço
        AddressableDomain owner = new AddressableDomain() {
            @Override
            public Long getId() {
                return 0L;
            }

            @Override
            public Long getIdUserOwner() {
                return 1L;
            }
        };

        addressDomain = AddressDomain.builder()
                .id(10L)
                .street("Rua Teste")
                .addressable(owner)
                .build();

        addressDTO = AddressDTO.builder()
                .id(10L)
                .street("Rua Teste")
                .build();

        userDTO = UserDTO.builder().id(1L).name("Test User").build();
        restaurantDTO = RestaurantDTO.builder().id(1L).name("Test Restaurant").build();
    }

    @Test
    void create_shouldReturnANewAddressGateway() {
        var newAddressGateway = AddressGateway.build(dataSource);

        Assertions.assertNotNull(newAddressGateway);
    }

    @Test
    @DisplayName("Deve criar um endereço para um usuário com sucesso")
    void createUserAddress_shouldCreateAddressForUser() {
        // Arrange
        when(dataSource.getUserById(any())).thenReturn(Optional.of(userDTO));
        when(addressPresenter.toAddressDTO(addressDomain, userDTO)).thenReturn(addressDTO);
        when(dataSource.createUserAddress(addressDTO)).thenReturn(addressDTO);
        when(addressPresenter.toDomain(addressDTO)).thenReturn(addressDomain);

        // Act
        AddressDomain result = addressGateway.createUserAddress(addressDomain);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);

        verify(dataSource, times(1)).getUserById(any());
        verify(addressPresenter).toAddressDTO(addressDomain, userDTO);
        verify(dataSource).createUserAddress(addressDTO);
        verify(addressPresenter).toDomain(addressDTO);
    }

    @Test
    @DisplayName("Deve criar um endereço para um restaurante com sucesso")
    void createRestaurantAddress_shouldCreateAddressForRestaurant() {
        // Arrange
        when(dataSource.getRestaurantById(any())).thenReturn(Optional.of(restaurantDTO));
        when(addressPresenter.toAddressDTO(addressDomain, restaurantDTO)).thenReturn(addressDTO);
        when(dataSource.createRestaurantAddress(addressDTO)).thenReturn(addressDTO);
        when(addressPresenter.toDomain(addressDTO)).thenReturn(addressDomain);

        // Act
        AddressDomain result = addressGateway.createRestaurantAddress(addressDomain);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);

        verify(dataSource).getRestaurantById(any());
        verify(addressPresenter).toAddressDTO(addressDomain, restaurantDTO);
        verify(dataSource).createRestaurantAddress(addressDTO);
        verify(addressPresenter).toDomain(addressDTO);
    }

    @Test
    @DisplayName("Deve atualizar um endereço com sucesso")
    void update_shouldReturnUpdatedAddressDomain() {
        // Arrange
        when(addressPresenter.toDTO(addressDomain)).thenReturn(addressDTO);
        when(dataSource.updateAddress(addressDTO)).thenReturn(addressDTO);
        when(addressPresenter.toDomain(addressDTO)).thenReturn(addressDomain);

        // Act
        AddressDomain result = addressGateway.update(addressDomain);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStreet()).isEqualTo("Rua Teste");

        verify(addressPresenter).toDTO(addressDomain);
        verify(dataSource).updateAddress(addressDTO);
        verify(addressPresenter).toDomain(addressDTO);
    }

    @Test
    @DisplayName("Deve retornar um endereço quando o ID existe")
    void getOneByid_whenAddressExists_shouldReturnAddressDomain() {
        // Arrange
        when(dataSource.getAddressById(10L)).thenReturn(Optional.of(addressDTO));
        when(addressPresenter.toDomain(addressDTO)).thenReturn(addressDomain);

        // Act
        AddressDomain result = addressGateway.getOneByid(10L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
    }

    @Test
    @DisplayName("Deve lançar ApplicationException quando o endereço não existe")
    void getOneByid_whenAddressDoesNotExist_shouldThrowException() {
        // Arrange
        when(dataSource.getAddressById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> addressGateway.getOneByid(99L))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("Address not found");
    }

    @Test
    @DisplayName("Deve retornar Optional de endereço quando o ID existe")
    void getById_whenAddressExists_shouldReturnOptionalOfDomain() {
        // Arrange
        when(dataSource.getAddressById(10L)).thenReturn(Optional.of(addressDTO));
        when(addressPresenter.toDomain(addressDTO)).thenReturn(addressDomain);

        // Act
        Optional<AddressDomain> result = addressGateway.getById(10L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(10L);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando o endereço não existe")
    void getById_whenAddressDoesNotExist_shouldReturnEmptyOptional() {
        // Arrange
        when(dataSource.getAddressById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<AddressDomain> result = addressGateway.getById(99L);

        // Assert
        assertThat(result).isNotPresent();
        verify(addressPresenter, never()).toDomain(any(AddressDTO.class));
    }

    @Test
    @DisplayName("Deve retornar uma página de endereços")
    void getAllPaged_shouldReturnPageOfAddressDomain() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 5);
        Page<AddressDTO> addressDTOPage = new PageImpl<>(List.of(addressDTO), pageable, 1);

        when(dataSource.getAllAddressPaged(pageable)).thenReturn(addressDTOPage);
        when(addressPresenter.toDomain(addressDTO)).thenReturn(addressDomain);

        // Act
        Page<AddressDomain> result = addressGateway.getAllPaged(pageable);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().getFirst().getStreet()).isEqualTo("Rua Teste");
    }

    @Test
    @DisplayName("Deve chamar o método de deleção do data source")
    void deleteById_shouldCallDataSourceDelete() {
        // Arrange
        Long addressId = 10L;
        doNothing().when(dataSource).deleteAddressById(addressId);

        // Act
        addressGateway.deleteById(addressId);

        // Assert
        verify(dataSource).deleteAddressById(addressId);
        verifyNoMoreInteractions(dataSource);
    }
}