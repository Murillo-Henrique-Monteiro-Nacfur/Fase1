package com.postech.fiap.fase1.webapi.data;

import com.postech.fiap.fase1.core.dto.address.AddressDTO;
import com.postech.fiap.fase1.core.dto.product.ProductDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import com.postech.fiap.fase1.webapi.data.entity.Address;
import com.postech.fiap.fase1.webapi.data.entity.Product;
import com.postech.fiap.fase1.webapi.data.entity.Restaurant;
import com.postech.fiap.fase1.webapi.data.entity.User;
import com.postech.fiap.fase1.webapi.data.mapper.AddressMapper;
import com.postech.fiap.fase1.webapi.data.mapper.ProductMapper;
import com.postech.fiap.fase1.webapi.data.mapper.RestaurantMapper;
import com.postech.fiap.fase1.webapi.data.mapper.UserMapper;
import com.postech.fiap.fase1.webapi.data.repository.AddressRepository;
import com.postech.fiap.fase1.webapi.data.repository.ProductRepository;
import com.postech.fiap.fase1.webapi.data.repository.RestaurantRepository;
import com.postech.fiap.fase1.webapi.data.repository.UserRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataRepositoryTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserMapper userMapper;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private AddressMapper addressMapper;
    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private DataRepository dataRepository;
    private User userEntity;
    private UserDTO userDTO;
    private Product productEntity;
    private ProductDTO productDTO;
    private Restaurant restaurantEntity;
    private RestaurantDTO restaurantDTO;
    private Address addressEntity;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        userDTO = UserDTO.builder().id(1L).name("Test User DTO").login("testuser").build();
        userEntity = User.builder().id(1L).name("Test User Entity").login("testuser").build();

        productDTO = ProductDTO.builder().id(10L).name("Test Product DTO").build();
        productEntity = Product.builder().id(10L).name("Test Product Entity").build();

        restaurantDTO = RestaurantDTO.builder().id(20L).name("Test Restaurant DTO").cnpj("12345678000199").build();
        restaurantEntity = Restaurant.builder().id(20L).name("Test Restaurant Entity").cnpj("12345678000199").build();

        addressDTO = AddressDTO.builder().id(30L).street("Test Street DTO").build();
        addressEntity = Address.builder().id(30L).street("Test Street Entity").build();
    }

    @Test
    @DisplayName("[User] Deve criar um usuário com sucesso")
    void createUser_shouldMapSaveAndReturnDTO() {
        when(userMapper.toEntity(userDTO)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);

        // Act
        UserDTO result = dataRepository.createUser(userDTO);

        // Assert
        assertThat(result).isNotNull().isEqualTo(userDTO);
        verify(userMapper).toEntity(userDTO);
        verify(userRepository).save(userEntity);
        verify(userMapper).toDTO(userEntity);
    }

    @Test
    @DisplayName("[User] Deve atualizar um usuário existente com sucesso")
    void updateUser_whenUserExists_shouldUpdateAndReturnDTO() {
        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.of(userEntity));
        when(userMapper.updateToEntity(userDTO, userEntity)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);

        // Act
        UserDTO result = dataRepository.updateUser(userDTO);

        // Assert
        assertThat(result).isNotNull().isEqualTo(userDTO);
        verify(userRepository).findById(userDTO.getId());
        verify(userMapper).updateToEntity(userDTO, userEntity);
        verify(userRepository).save(userEntity);
    }

    @Test
    @DisplayName("[User] Deve lançar ApplicationException ao tentar atualizar um usuário inexistente")
    void updateUser_whenUserDoesNotExist_shouldThrowException() {
        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> dataRepository.updateUser(userDTO))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("User not found");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("[User] Deve deletar um usuário pelo ID")
    void deleteUserById_shouldCallRepositoryDelete() {
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        // Act
        dataRepository.deleteUserById(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("[User] Deve retornar um usuário pelo login quando ele existe")
    void getUserByLogin_whenUserExists_shouldReturnOptionalOfDTO() {
        when(userRepository.findByLogin("testuser")).thenReturn(Optional.of(userEntity));
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);

        // Act
        Optional<UserDTO> result = dataRepository.getUserByLogin("testuser");

        // Assert
        assertThat(result).isPresent().contains(userDTO);
    }

    @Test
    @DisplayName("[User] Deve retornar Optional vazio pelo login quando usuário não existe")
    void getUserByLogin_whenUserDoesNotExist_shouldReturnEmptyOptional() {
        when(userRepository.findByLogin("nouser")).thenReturn(Optional.empty());

        Optional<UserDTO> result = dataRepository.getUserByLogin("nouser");

        // Assert
        assertThat(result).isEmpty();
        verify(userMapper, never()).toDTO(any(User.class));
    }

    @Test
    @DisplayName("[Product] Deve criar um produto com sucesso")
    void createProduct_shouldMapSaveAndReturnDTO() {
        when(productMapper.toEntity(productDTO)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productMapper.toDTO(productEntity)).thenReturn(productDTO);

        // Act
        ProductDTO result = dataRepository.createProduct(productDTO);

        // Assert
        assertThat(result).isNotNull().isEqualTo(productDTO);
        verify(productMapper).toEntity(productDTO);
        verify(productRepository).save(productEntity);
        verify(productMapper).toDTO(productEntity);
    }

    @Test
    @DisplayName("[Product] Deve retornar um produto pelo ID quando ele existe")
    void getById_whenProductExists_shouldReturnDTO() {
        when(productRepository.findProductById(productDTO.getId())).thenReturn(Optional.of(productEntity));
        when(productMapper.toDTO(productEntity)).thenReturn(productDTO);

        // Act
        ProductDTO result = dataRepository.getById(productDTO.getId());

        // Assert
        assertThat(result).isNotNull().isEqualTo(productDTO);
    }

    @Test
    @DisplayName("[Product] Deve lançar ApplicationException ao buscar um produto por ID que não existe")
    void getById_whenProductDoesNotExist_shouldThrowException() {
        when(productRepository.findProductById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> dataRepository.getById(999L))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("Product not found");
    }

    @Test
    @DisplayName("[Product] Deve retornar uma lista de produtos pelo ID do restaurante")
    void getProductByIdRestaurant_shouldReturnListOfDTOs() {
        Long restaurantId = 1L;
        when(productRepository.findProductByRestaurantId(restaurantId)).thenReturn(List.of(productEntity));
        when(productMapper.toDTO(productEntity)).thenReturn(productDTO);

        // Act
        List<ProductDTO> result = dataRepository.getProductByIdRestaurant(restaurantId);

        // Assert
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.getFirst()).isEqualTo(productDTO);
        verify(productRepository).findProductByRestaurantId(restaurantId);
        verify(productMapper).toDTO(productEntity);
    }

    @Test
    @DisplayName("[Product] Deve deletar um produto pelo ID")
    void deleteProductById_shouldCallRepositoryDelete() {
        Long productId = 10L;
        doNothing().when(productRepository).deleteById(productId);

        // Act
        dataRepository.deleteProductById(productId);

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    @DisplayName("[Restaurant] Deve criar um restaurante com sucesso")
    void createRestaurant_shouldMapSaveAndReturnDTO() {
        when(restaurantMapper.toEntity(restaurantDTO)).thenReturn(restaurantEntity);
        when(restaurantRepository.save(restaurantEntity)).thenReturn(restaurantEntity);
        when(restaurantMapper.toDTO(restaurantEntity)).thenReturn(restaurantDTO);

        // Act
        RestaurantDTO result = dataRepository.createRestaurant(restaurantDTO);

        // Assert
        assertThat(result).isNotNull().isEqualTo(restaurantDTO);
        verify(restaurantMapper).toEntity(restaurantDTO);
        verify(restaurantRepository).save(restaurantEntity);
        verify(restaurantMapper).toDTO(restaurantEntity);
    }

    @Test
    @DisplayName("[Restaurant] Deve atualizar um restaurante existente")
    void updateRestaurant_whenExists_shouldUpdateAndReturnDTO() {
        when(restaurantRepository.findById(restaurantDTO.getId())).thenReturn(Optional.of(restaurantEntity));
        when(restaurantMapper.updateToEntity(restaurantDTO, restaurantEntity)).thenReturn(restaurantEntity);
        when(restaurantRepository.save(restaurantEntity)).thenReturn(restaurantEntity);
        when(restaurantMapper.toDTO(restaurantEntity)).thenReturn(restaurantDTO);

        // Act
        RestaurantDTO result = dataRepository.updateRestaurant(restaurantDTO);

        // Assert
        assertThat(result).isNotNull().isEqualTo(restaurantDTO);
        verify(restaurantRepository).findById(restaurantDTO.getId());
        verify(restaurantMapper).updateToEntity(restaurantDTO, restaurantEntity);
        verify(restaurantRepository).save(restaurantEntity);
    }

    @Test
    @DisplayName("[Restaurant] Deve retornar uma página de restaurantes")
    void getAllRestaurantPaged_shouldReturnPageOfDTOs() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Restaurant> restaurantPage = new PageImpl<>(List.of(restaurantEntity), pageable, 1);
        when(restaurantRepository.findAll(pageable)).thenReturn(restaurantPage);
        when(restaurantMapper.toDTO(restaurantEntity)).thenReturn(restaurantDTO);
        // Act
        Page<RestaurantDTO> resultPage = dataRepository.getAllRestaurantPaged(pageable);

        // Assert
        assertThat(resultPage).isNotNull();
        assertThat(resultPage.getTotalElements()).isEqualTo(1);
        assertThat(resultPage.getContent().getFirst()).isEqualTo(restaurantDTO);
    }

    @Test
    @DisplayName("[Address] Deve criar um endereço de usuário")
    void createUserAddress_shouldMapSaveAndReturnDTO() {
        when(addressMapper.toEntityUser(addressDTO)).thenReturn(addressEntity);
        when(addressRepository.save(addressEntity)).thenReturn(addressEntity);
        when(addressMapper.toDTO(addressEntity)).thenReturn(addressDTO);

        // Act
        AddressDTO result = dataRepository.createUserAddress(addressDTO);

        // Assert
        assertThat(result).isNotNull().isEqualTo(addressDTO);
        verify(addressMapper).toEntityUser(addressDTO);
        verify(addressRepository).save(addressEntity);
        verify(addressMapper).toDTO(addressEntity);
    }

    @Test
    @DisplayName("[Address] Deve atualizar um endereço existente")
    void updateAddress_whenExists_shouldUpdateAndReturnDTO() {
        when(addressRepository.findById(addressDTO.getId())).thenReturn(Optional.of(addressEntity));
        when(addressMapper.updateToEntity(addressEntity, addressDTO)).thenReturn(addressEntity);
        when(addressRepository.save(addressEntity)).thenReturn(addressEntity);
        when(addressMapper.toDTO(addressEntity)).thenReturn(addressDTO);

        // Act
        AddressDTO result = dataRepository.updateAddress(addressDTO);

        // Assert
        assertThat(result).isNotNull().isEqualTo(addressDTO);
        verify(addressRepository).findById(addressDTO.getId());
        verify(addressMapper).updateToEntity(addressEntity, addressDTO);
        verify(addressRepository).save(addressEntity);
    }

    @Test
    @DisplayName("[Address] Deve lançar ApplicationException ao tentar atualizar um endereço inexistente")
    void updateAddress_whenDoesNotExist_shouldThrowException() {
        when(addressRepository.findById(addressDTO.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> dataRepository.updateAddress(addressDTO))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("Address not found");
        verify(addressRepository, never()).save(any(Address.class));
    }


    @Test
    @DisplayName("[User] Deve retornar true se existe usuário com o mesmo email")
    void hasUserWithSameEmail_shouldReturnTrueWhenExists() {
        when(userRepository.hasUserWithSameEmail(1L, "test@example.com")).thenReturn(true);

        // Act
        boolean result = dataRepository.hasUserWithSameEmail(1L, "test@example.com");

        // Assert
        assertThat(result).isTrue();
        verify(userRepository).hasUserWithSameEmail(1L, "test@example.com");
    }

    @Test
    @DisplayName("[User] Deve retornar true se existe usuário com o mesmo login")
    void hasUserWithSameLogin_shouldReturnTrueWhenExists() {
        when(userRepository.hasUserWithSameLogin(1L, "testlogin")).thenReturn(true);

        // Act
        boolean result = dataRepository.hasUserWithSameLogin(1L, "testlogin");

        // Assert
        assertThat(result).isTrue();
        verify(userRepository).hasUserWithSameLogin(1L, "testlogin");
    }

    @Test
    @DisplayName("[User] Deve retornar um usuário pelo ID quando ele existe")
    void getUserById_whenUserExists_shouldReturnOptionalOfDTO() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);

        // Act
        Optional<UserDTO> result = dataRepository.getUserById(1L);

        // Assert
        assertThat(result).isPresent().contains(userDTO);
    }

    @Test
    @DisplayName("[User] Deve retornar Optional vazio quando usuário não existe")
    void getUserById_whenUserDoesNotExist_shouldReturnEmptyOptional() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<UserDTO> result = dataRepository.getUserById(99L);

        // Assert
        assertThat(result).isEmpty();
        verify(userMapper, never()).toDTO(any(User.class));
    }

    @Test
    @DisplayName("[User] Deve retornar uma página de usuários")
    void getAllUserPaged_shouldReturnPageOfDTOs() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(List.of(userEntity), pageable, 1);
        when(userRepository.findAll(pageable)).thenReturn(userPage);
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);

        // Act
        Page<UserDTO> resultPage = dataRepository.getAllUserPaged(pageable);

        // Assert
        assertThat(resultPage).isNotNull();
        assertThat(resultPage.getTotalElements()).isEqualTo(1);
        assertThat(resultPage.getContent().getFirst()).isEqualTo(userDTO);
    }

    @Test
    @DisplayName("[User] Deve atualizar a senha de um usuário")
    void updateUserPassword_shouldMapSaveAndReturnDTO() {
        when(userMapper.toEntity(userDTO)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);

        // Act
        UserDTO result = dataRepository.updateUserPassoword(userDTO);

        // Assert
        assertThat(result).isNotNull().isEqualTo(userDTO);
        verify(userMapper).toEntity(userDTO);
        verify(userRepository).save(userEntity);
        verify(userMapper).toDTO(userEntity);
    }

    @Test
    @DisplayName("[Address] Deve criar um endereço de restaurante")
    void createRestaurantAddress_shouldMapSaveAndReturnDTO() {
        when(addressMapper.toEntityRestaurant(addressDTO)).thenReturn(addressEntity);
        when(addressRepository.save(addressEntity)).thenReturn(addressEntity);
        when(addressMapper.toDTO(addressEntity)).thenReturn(addressDTO);

        // Act
        AddressDTO result = dataRepository.createRestaurantAddress(addressDTO);

        // Assert
        assertThat(result).isNotNull().isEqualTo(addressDTO);
        verify(addressMapper).toEntityRestaurant(addressDTO);
        verify(addressRepository).save(addressEntity);
        verify(addressMapper).toDTO(addressEntity);
    }

    @Test
    @DisplayName("[Address] Deve retornar um endereço pelo ID quando ele existe")
    void getAddressById_whenAddressExists_shouldReturnOptionalOfDTO() {
        when(addressRepository.findById(30L)).thenReturn(Optional.of(addressEntity));
        when(addressMapper.toDTO(addressEntity)).thenReturn(addressDTO);

        // Act
        Optional<AddressDTO> result = dataRepository.getAddressById(30L);

        // Assert
        assertThat(result).isPresent().contains(addressDTO);
    }

    @Test
    @DisplayName("[Address] Deve retornar uma página de endereços")
    void getAllAddressPaged_shouldReturnPageOfDTOs() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Address> addressPage = new PageImpl<>(List.of(addressEntity), pageable, 1);
        when(addressRepository.findAll(pageable)).thenReturn(addressPage);
        when(addressMapper.toDTO(addressEntity)).thenReturn(addressDTO);

        // Act
        Page<AddressDTO> resultPage = dataRepository.getAllAddressPaged(pageable);

        // Assert
        assertThat(resultPage).isNotNull();
        assertThat(resultPage.getTotalElements()).isEqualTo(1);
        assertThat(resultPage.getContent().getFirst()).isEqualTo(addressDTO);
    }

    @Test
    @DisplayName("[Address] Deve deletar um endereço pelo ID")
    void deleteAddressById_shouldCallRepositoryDelete() {
        Long addressId = 30L;
        doNothing().when(addressRepository).deleteById(addressId);

        // Act
        dataRepository.deleteAddressById(addressId);

        // Assert
        verify(addressRepository, times(1)).deleteById(addressId);
    }

    @Test
    @DisplayName("[Restaurant] Deve retornar true se existe restaurante com o mesmo CNPJ")
    void hasRestaurantWithCNPJ_shouldReturnTrueWhenExists() {
        when(restaurantRepository.hasRestaurantWithCNPJ(20L, "12345678000199")).thenReturn(true);

        // Act
        boolean result = dataRepository.hasRestaurantWithCNPJ(20L, "12345678000199");

        // Assert
        assertThat(result).isTrue();
        verify(restaurantRepository).hasRestaurantWithCNPJ(20L, "12345678000199");
    }

    @Test
    @DisplayName("[Restaurant] Deve retornar um restaurante pelo ID quando ele existe")
    void getRestaurantById_whenRestaurantExists_shouldReturnOptionalOfDTO() {
        when(restaurantRepository.findById(20L)).thenReturn(Optional.of(restaurantEntity));
        when(restaurantMapper.toDTO(restaurantEntity)).thenReturn(restaurantDTO);

        // Act
        Optional<RestaurantDTO> result = dataRepository.getRestaurantById(20L);

        // Assert
        assertThat(result).isPresent().contains(restaurantDTO);
    }

    @Test
    @DisplayName("[Restaurant] Deve deletar um restaurante pelo ID")
    void deleteRestaurantById_shouldCallRepositoryDelete() {
        Long restaurantId = 20L;
        doNothing().when(restaurantRepository).deleteById(restaurantId);

        // Act
        dataRepository.deleteRestaurantById(restaurantId);

        // Assert
        verify(restaurantRepository, times(1)).deleteById(restaurantId);
    }

    @Test
    @DisplayName("[Restaurant] Deve lançar ApplicationException ao buscar um restaurante por ID que não existe em getOneById")
    void getOneById_whenRestaurantDoesNotExist_shouldThrowException() {
        Long nonExistentId = 999L;
        when(restaurantRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> dataRepository.getOneById(nonExistentId))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("Restaurant Not found");
        verify(restaurantRepository, times(1)).findById(nonExistentId);
        verifyNoInteractions(restaurantMapper);
    }
}