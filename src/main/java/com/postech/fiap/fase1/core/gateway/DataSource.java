package com.postech.fiap.fase1.core.gateway;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.dto.product.ProductDTO;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DataSource {
    //Address
    AddressDomain createAddress(AddressDomain addressDomain);

    AddressDomain updateAddress(AddressDomain addressDomain);

    Optional<AddressDomain> getAddressById(Long idAddress);

    Page<AddressDomain> getAllAddressPaged(Pageable pageable);

    void deleteAddressById(Long idAddress);

    //Restaurant
    RestaurantDomain createRestaurant(RestaurantDomain restaurantDomain);

    RestaurantDomain updateRestaurant(RestaurantDomain restaurantDomain);

    boolean hasRestaurantWithCNPJ(Long idRestaurent, String cnpj);

    Optional<RestaurantDomain> getRestaurantById(Long idRestaurent);

    Page<RestaurantDomain> getAllRestaurantPaged(Pageable pageable);

    void deleteRestaurantById(Long idRestaurant);

    //User
    boolean hasUserWithSameEmail(Long idUser, String email);

    boolean hasUserWithSameLogin(Long idUser, String email);

    Optional<UserDTO> getUserById(Long idRestaurent);

    Page<UserDTO> getAllUserPaged(Pageable pageable);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO);

    UserDTO updateUserPassoword(UserDTO userDTO);

    void deleteUserById(Long idUser);

    Optional<UserDTO> getUserByLogin(@NotBlank String login);

    //Product
    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(ProductDTO productDTO);
}
