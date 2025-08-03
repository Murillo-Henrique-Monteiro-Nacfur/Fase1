package com.postech.fiap.fase1.infrastructure.data;

import com.postech.fiap.fase1.core.dto.address.AddressDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.infrastructure.data.entity.Address;
import com.postech.fiap.fase1.infrastructure.data.entity.Restaurant;
import com.postech.fiap.fase1.infrastructure.data.entity.User;
import com.postech.fiap.fase1.infrastructure.data.mapper.AddressMapper;
import com.postech.fiap.fase1.infrastructure.data.mapper.RestaurantMapper;
import com.postech.fiap.fase1.infrastructure.data.mapper.UserMapper;
import com.postech.fiap.fase1.infrastructure.data.repository.AddressRepository;
import com.postech.fiap.fase1.infrastructure.data.repository.RestaurantRepository;
import com.postech.fiap.fase1.infrastructure.data.repository.UserRepository;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataRepository implements DataSource {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final RestaurantMapper restaurantMapper;

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ApplicationException("USER_NOT_FOUND"));
    }

    @Transactional(readOnly = true)
    public boolean hasUserWithSameEmail(Long idUser, String email) {
        return userRepository.hasUserWithSameEmail(idUser, email);
    }

    @Transactional(readOnly = true)
    public boolean hasUserWithSameLogin(Long idUser, String login) {
        return userRepository.hasUserWithSameLogin(idUser, login);


    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserById(Long idRestaurent) {
        return userRepository
                .findById(idRestaurent)
                .map(userMapper::toDTO)
                .or(Optional::empty);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> getAllUserPaged(Pageable pageable) {
        return userRepository.findAll(pageable).
                map(userMapper::toDTO);
    }

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDomain) {
        User user = userMapper.toEntity(userDomain);
        return userMapper.toDTO(userRepository.save(user));
    }



    @Override
    @Transactional
    public UserDTO updateUser(UserDTO userDomain) {
        User user = findUserById(userDomain.getId());
        user = userMapper.updateToEntity(userDomain, user);
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUserPassoword(UserDTO userDTO){
        return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));
    }

    @Override
    public void deleteUserById(Long idUser) {
        userRepository.deleteById(idUser);
    }

    @Override
    public Optional<UserDTO> getUserByLogin(String login) {
        return userRepository.findByLogin(login).map(userMapper::toDTO)
                .or(Optional::empty);
    }

    //address
    @Override
    public AddressDTO createUserAddress(AddressDTO addressDTO) {
        final Address address = addressMapper.toEntityUser(addressDTO);
        return addressMapper.toDTO(addressRepository.save(address));
    }
    @Override
    public AddressDTO createRestaurantAddress(AddressDTO addressDTO) {
        final Address address = addressMapper.toEntityRestaurant(addressDTO);
        return addressMapper.toDTO(addressRepository.save(address));
    }

    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO) {
        var oldAddress = findAddressById(addressDTO.getId());
        final Address address = addressMapper.updateToEntity(oldAddress, addressDTO);
        return addressMapper.toDTO(addressRepository.save(address));
    }
    private Address findAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow(
                () -> new ApplicationException("ADDRESS_NOT_FOUND"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AddressDTO> getAddressById(Long idAddress) {
        return addressRepository
                .findById(idAddress)
                .map(addressMapper::toDTO)
                .or(Optional::empty);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<AddressDTO> getAllAddressPaged(Pageable pageable) {
        return addressRepository.findAll(pageable).
                map(addressMapper::toDTO);
    }

    @Override
    public void deleteAddressById(Long idRestaurant) {
        addressRepository.deleteById(idRestaurant);
    }

    //restaurant
    @Override
    public RestaurantDTO createRestaurant(RestaurantDTO restaurant) {
        return restaurantMapper.toDTO(restaurantRepository.save(restaurantMapper.toEntity(restaurant)));
    }


    @Override
    public RestaurantDTO updateRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = getOneById(restaurantDTO.getId());
        restaurant = restaurantMapper.updateToEntity(restaurantDTO, restaurant);
        return restaurantMapper.toDTO(restaurantRepository.save(restaurant));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasRestaurantWithCNPJ(Long idRestaurant, String cnpj) {
        return restaurantRepository.hasRestaurantWithCNPJ(idRestaurant, cnpj);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RestaurantDTO> getRestaurantById(Long idRestaurent) {
        return restaurantRepository
                .findById(idRestaurent)
                .map(restaurantMapper::toDTO)
                .or(Optional::empty);
    }

    public Restaurant getOneById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(
                () -> new ApplicationException("Restaurant Not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RestaurantDTO> getAllRestaurantPaged(Pageable pageable) {
        return restaurantRepository.findAll(pageable).
                map(restaurantMapper::toDTO);
    }

    @Override
    public void deleteRestaurantById(Long idRestaurant) {
        restaurantRepository.deleteById(idRestaurant);
    }
}
