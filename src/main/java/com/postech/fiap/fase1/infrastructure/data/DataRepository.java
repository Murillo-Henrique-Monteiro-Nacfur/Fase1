package com.postech.fiap.fase1.infrastructure.data;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.infrastructure.data.entity.User;
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

    private User findById(Long id) {
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
        User user = findById(userDomain.getId());
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

    @Override
    public AddressDomain createAddress(AddressDomain addressDomain) {
        return null;
    }

    @Override
    public AddressDomain updateAddress(AddressDomain addressDomain) {
        return null;
    }

    @Override
    public Optional<AddressDomain> getAddressById(Long idAddress) {
        return Optional.empty();
    }

    @Override
    public Page<AddressDomain> getAllAddressPaged(Pageable pageable) {
        return null;
    }

    @Override
    public void deleteAddressById(Long idAddress) {

    }

    @Override
    public RestaurantDomain createRestaurant(RestaurantDomain restaurantDomain) {
        return null;
    }

    @Override
    public RestaurantDomain updateRestaurant(RestaurantDomain restaurantDomain) {
        return null;
    }

    @Override
    public boolean hasRestaurantWithCNPJ(Long idRestaurent, String cnpj) {
        return false;
    }

    @Override
    public Optional<RestaurantDomain> getRestaurantById(Long idRestaurent) {
        return Optional.empty();
    }

    @Override
    public Page<RestaurantDomain> getAllRestaurantPaged(Pageable pageable) {
        return null;
    }

    @Override
    public void deleteRestaurantById(Long idRestaurant) {

    }
}
