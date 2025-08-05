package com.postech.fiap.fase1.webapi.data.mapper;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressMapperTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private AddressMapper addressMapper;

    private AddressDTO addressDTOForUser;
    private AddressDTO addressDTOForRestaurant;
    private UserDTO userDTO;
    private RestaurantDTO restaurantDTO;
    private User userEntity;
    private Restaurant restaurantEntity;
    private Address addressEntityWithUser;
    private Address addressEntityWithRestaurant;

    @BeforeEach
    void setUp() {
        userDTO = UserDTO.builder().id(1L).build();
        restaurantDTO = RestaurantDTO.builder().id(10L).build();

        addressDTOForUser = AddressDTO.builder()
                .id(1L)
                .street("User Street")
                .number("123")
                .city("User City")
                .state("US")
                .zipCode("12345")
                .country("USA")
                .neighborhood("User Neighborhood")
                .addressable(userDTO)
                .build();

        addressDTOForRestaurant = AddressDTO.builder()
                .id(2L)
                .street("Restaurant Street")
                .number("456")
                .city("Restaurant City")
                .state("RS")
                .zipCode("54321")
                .country("RSA")
                .neighborhood("Restaurant Neighborhood")
                .addressable(restaurantDTO)
                .build();

        userEntity = User.builder().id(1L).build();
        restaurantEntity = Restaurant.builder().id(10L).build();

        addressEntityWithUser = Address.builder()
                .id(1L)
                .street("User Street")
                .addressable(userEntity)
                .build();

        addressEntityWithRestaurant = Address.builder()
                .id(2L)
                .street("Restaurant Street")
                .addressable(restaurantEntity)
                .build();
    }

    @Test
    @DisplayName("Deve mapear AddressDTO para Address entity com um Restaurant")
    void toEntityRestaurant_shouldMapCorrectly() {
        when(restaurantMapper.toEntity(any(RestaurantDTO.class))).thenReturn(restaurantEntity);

        Address result = addressMapper.toEntityRestaurant(addressDTOForRestaurant);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(addressDTOForRestaurant.getId());
        assertThat(result.getStreet()).isEqualTo(addressDTOForRestaurant.getStreet());
        assertThat(result.getAddressable()).isInstanceOf(Restaurant.class);
        assertThat(result.getAddressable().getId()).isEqualTo(restaurantDTO.getId());

        verify(restaurantMapper).toEntity(any(RestaurantDTO.class));
    }

    @Test
    @DisplayName("Deve mapear AddressDTO para Address entity com um User")
    void toEntityUser_shouldMapCorrectly() {
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(userEntity);

        Address result = addressMapper.toEntityUser(addressDTOForUser);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(addressDTOForUser.getId());
        assertThat(result.getStreet()).isEqualTo(addressDTOForUser.getStreet());
        assertThat(result.getAddressable()).isInstanceOf(User.class);
        assertThat(result.getAddressable().getId()).isEqualTo(userDTO.getId());

        verify(userMapper).toEntity(any(UserDTO.class));
    }

    @Test
    @DisplayName("Deve atualizar uma entidade Address com dados de um AddressDTO")
    void updateToEntity_shouldUpdateFields() {
        Address existingAddress = new Address();
        existingAddress.setId(99L);

        Address updatedAddress = addressMapper.updateToEntity(existingAddress, addressDTOForUser);

        assertThat(updatedAddress).isSameAs(existingAddress); // Deve ser a mesma instância
        assertThat(updatedAddress.getId()).isEqualTo(99L); // ID não deve ser alterado
        assertThat(updatedAddress.getStreet()).isEqualTo(addressDTOForUser.getStreet());
        assertThat(updatedAddress.getNumber()).isEqualTo(addressDTOForUser.getNumber());
        assertThat(updatedAddress.getCity()).isEqualTo(addressDTOForUser.getCity());
        assertThat(updatedAddress.getState()).isEqualTo(addressDTOForUser.getState());
        assertThat(updatedAddress.getZipCode()).isEqualTo(addressDTOForUser.getZipCode());
        assertThat(updatedAddress.getCountry()).isEqualTo(addressDTOForUser.getCountry());
        assertThat(updatedAddress.getNeighborhood()).isEqualTo(addressDTOForUser.getNeighborhood());
    }

    @Test
    @DisplayName("Deve mapear Address entity (com User) para AddressDTO")
    void toDTO_whenAddressableIsUser_shouldMapCorrectly() {
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);

        AddressDTO result = addressMapper.toDTO(addressEntityWithUser);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(addressEntityWithUser.getId());
        assertThat(result.getStreet()).isEqualTo(addressEntityWithUser.getStreet());
        assertThat(result.getAddressable()).isInstanceOf(UserDTO.class);
        assertThat(result.getAddressable().getId()).isEqualTo(userEntity.getId());

        verify(userMapper).toDTO(userEntity);
    }

    @Test
    @DisplayName("Deve mapear Address entity (com Restaurant) para AddressDTO")
    void toDTO_whenAddressableIsRestaurant_shouldMapCorrectly() {
        when(restaurantMapper.toDTO(restaurantEntity)).thenReturn(restaurantDTO);

        AddressDTO result = addressMapper.toDTO(addressEntityWithRestaurant);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(addressEntityWithRestaurant.getId());
        assertThat(result.getStreet()).isEqualTo(addressEntityWithRestaurant.getStreet());
        assertThat(result.getAddressable()).isInstanceOf(RestaurantDTO.class);
        assertThat(result.getAddressable().getId()).isEqualTo(restaurantEntity.getId());

        verify(restaurantMapper).toDTO(restaurantEntity);
    }

    @Test
    @DisplayName("Deve mapear uma lista de Address entities para uma lista de AddressDTOs")
    void toDTO_whenGivenList_shouldMapListCorrectly() {
        List<Address> addressList = List.of(addressEntityWithUser, addressEntityWithRestaurant);
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);
        when(restaurantMapper.toDTO(restaurantEntity)).thenReturn(restaurantDTO);

        List<AddressDTO> resultList = addressMapper.toDTO(addressList);

        assertThat(resultList).isNotNull().hasSize(2);
        assertThat(resultList.get(0).getAddressable()).isInstanceOf(UserDTO.class);
        assertThat(resultList.get(0).getId()).isEqualTo(addressEntityWithUser.getId());
        assertThat(resultList.get(1).getAddressable()).isInstanceOf(RestaurantDTO.class);
        assertThat(resultList.get(1).getId()).isEqualTo(addressEntityWithRestaurant.getId());
    }
}