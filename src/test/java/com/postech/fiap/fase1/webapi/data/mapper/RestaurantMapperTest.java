package com.postech.fiap.fase1.webapi.data.mapper;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.dto.address.AddressDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import com.postech.fiap.fase1.webapi.data.entity.Address;
import com.postech.fiap.fase1.webapi.data.entity.Restaurant;
import com.postech.fiap.fase1.webapi.data.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantMapperTest {

    @Mock
    private AddressMapper addressMapper;
    @InjectMocks
    private RestaurantMapper restaurantMapper;
    @Mock
    private UserMapper userMapper;

    private Restaurant restaurantEntity;
    private RestaurantDTO restaurantDTO;

    @BeforeEach
    void setUp() {
        User user = User.builder().id(1L).build();
        UserDTO userDto = UserDTO.builder().id(1L).build();
        AddressDTO addressDTO = AddressDTO.builder()
                .id(10L)
                .street("Rua Teste")
                .city("Cidade Teste")
                .build();

        Address addressEntity = Address.builder()
                .id(10L)
                .street("Rua Teste")
                .city("Cidade Teste")
                .build();

        restaurantDTO = RestaurantDTO.builder()
                .id(1L)
                .name("Restaurante Teste")
                .description("Um ótimo lugar para comer")
                .cuisineType("Brasileira")
                .cnpj("12345678000199")
                .openTime(LocalTime.of(11, 30))
                .closeTime(LocalTime.of(22, 0))
                .user(userDto)
                .address(List.of(addressDTO))
                .build();

        restaurantEntity = Restaurant.builder()
                .id(1L)
                .name("Restaurante Teste")
                .description("Um ótimo lugar para comer")
                .cuisineType("Brasileira")
                .cnpj("12345678000199")
                .openTime(LocalTime.of(11, 30))
                .closeTime(LocalTime.of(22, 0))
                .user(user)
                .addresses(List.of(addressEntity))
                .build();
    }

    @Test
    @DisplayName("Deve mapear RestaurantDTO para a entidade Restaurant corretamente")
    void shouldMapDtoToEntity() {
        when(userMapper.toEntity(restaurantDTO.getUser())).thenReturn(restaurantEntity.getUser());

        Restaurant resultEntity = restaurantMapper.toEntity(restaurantDTO);

        assertThat(resultEntity).isNotNull();
        assertThat(resultEntity.getName()).isEqualTo(restaurantDTO.getName());
        assertThat(resultEntity.getCnpj()).isEqualTo(restaurantDTO.getCnpj());
        assertThat(resultEntity.getUser().getId()).isEqualTo(restaurantDTO.getUser().getId());
    }

    @Test
    @DisplayName("Deve mapear a entidade Restaurant para RestaurantDTO corretamente")
    void shouldMapEntityToDto() {
        when(userMapper.toDTO(restaurantEntity.getUser())).thenReturn(restaurantDTO.getUser());

        RestaurantDTO resultDTO = restaurantMapper.toDTO(restaurantEntity);

        assertThat(resultDTO).isNotNull();
        assertThat(resultDTO.getName()).isEqualTo(restaurantEntity.getName());
        assertThat(resultDTO.getCnpj()).isEqualTo(restaurantEntity.getCnpj());
        assertThat(resultDTO.getUser().getId()).isEqualTo(restaurantEntity.getUser().getId());
    }

    @Test
    @DisplayName("Deve retornar nulo ao mapear DTO nulo para entidade")
    void shouldReturnNullWhenMappingNullDtoToEntity() {
        Restaurant result = restaurantMapper.toEntity(null);

        assertThat(result).isNull();

        verifyNoInteractions(addressMapper);
    }
    @Test
    @DisplayName("Deve atualizar uma entidade Restaurant com dados de um RestaurantDTO")
    void shouldUpdateEntityFromDto() {
        Restaurant entityToUpdate = Restaurant.builder()
                .id(1L)
                .name("Nome Antigo")
                .description("Descrição Antiga")
                .cuisineType("Antiga")
                .cnpj("00000000000000")
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(18, 0))
                .user(restaurantEntity.getUser())
                .build();

        Restaurant updatedEntity = restaurantMapper.updateToEntity(restaurantDTO, entityToUpdate);

        assertThat(updatedEntity).isSameAs(entityToUpdate);

        assertThat(updatedEntity.getName()).isEqualTo(restaurantDTO.getName());
        assertThat(updatedEntity.getDescription()).isEqualTo(restaurantDTO.getDescription());
        assertThat(updatedEntity.getCuisineType()).isEqualTo(restaurantDTO.getCuisineType());
        assertThat(updatedEntity.getCnpj()).isEqualTo(restaurantDTO.getCnpj());
        assertThat(updatedEntity.getOpenTime()).isEqualTo(restaurantDTO.getOpenTime());
        assertThat(updatedEntity.getCloseTime()).isEqualTo(restaurantDTO.getCloseTime());
        assertThat(updatedEntity.getId()).isEqualTo(entityToUpdate.getId());
        assertThat(updatedEntity.getUser()).isEqualTo(entityToUpdate.getUser());
        verifyNoInteractions(userMapper, addressMapper);
    }

    @Test
    @DisplayName("Deve mapear a entidade Restaurant para RestaurantDomain corretamente")
    void shouldMapEntityToDomain() {
        // Act
        RestaurantDomain resultDomain = restaurantMapper.toDomain(restaurantEntity);

        // Assert
        assertThat(resultDomain)
                .isNotNull()
                .extracting(
                        RestaurantDomain::getId,
                        RestaurantDomain::getName,
                        RestaurantDomain::getDescription,
                        RestaurantDomain::getCuisineType,
                        RestaurantDomain::getCnpj,
                        RestaurantDomain::getOpenTime,
                        RestaurantDomain::getCloseTime
                )
                .containsExactly(
                        restaurantEntity.getId(),
                        restaurantEntity.getName(),
                        restaurantEntity.getDescription(),
                        restaurantEntity.getCuisineType(),
                        restaurantEntity.getCnpj(),
                        restaurantEntity.getOpenTime(),
                        restaurantEntity.getCloseTime()
                );

        verifyNoInteractions(userMapper, addressMapper);
    }

    @Test
    @DisplayName("Deve mapear um RestaurantDTO para outro RestaurantDTO (sem usuário e endereço)")
    void shouldMapDtoToDto() {
        // Act
        RestaurantDTO resultDTO = restaurantMapper.toDTO(restaurantDTO);

        // Assert
        assertThat(resultDTO).isNotNull();
        assertThat(resultDTO.getId()).isEqualTo(restaurantDTO.getId());
        assertThat(resultDTO.getName()).isEqualTo(restaurantDTO.getName());
        assertThat(resultDTO.getCnpj()).isEqualTo(restaurantDTO.getCnpj());

        // Verifica que os campos complexos (user, address) não foram copiados, conforme a implementação.
        assertThat(resultDTO.getUser()).isNull();
        assertThat(resultDTO.getAddress()).isNull();

        verifyNoInteractions(userMapper, addressMapper);
    }

}