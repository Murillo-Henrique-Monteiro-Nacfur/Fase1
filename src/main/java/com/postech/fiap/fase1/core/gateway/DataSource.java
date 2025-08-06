package com.postech.fiap.fase1.core.gateway;

import com.postech.fiap.fase1.core.dto.address.AddressDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.dto.product.ProductDTO;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DataSource {
    //Address
    AddressDTO createUserAddress(AddressDTO addressDomain);

    AddressDTO createRestaurantAddress(AddressDTO addressDomain);
    AddressDTO updateAddress(AddressDTO addressDomain);

    Optional<AddressDTO> getAddressById(Long idAddress);

    Page<AddressDTO> getAllAddressPaged(Pageable pageable);

    void deleteAddressById(Long idAddress);

    //Restaurant
    RestaurantDTO createRestaurant(RestaurantDTO restaurantDomain);

    RestaurantDTO updateRestaurant(RestaurantDTO restaurantDomain);

    boolean hasRestaurantWithCNPJ(Long idRestaurent, String cnpj);

    Optional<RestaurantDTO> getRestaurantById(Long idRestaurent);

    Page<RestaurantDTO> getAllRestaurantPaged(Pageable pageable);

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

    ProductDTO getById(Long idProduct);

    List<ProductDTO> getProductByIdRestaurant(Long idRestaurant);
    void deleteProductById(Long idProduct);
}
