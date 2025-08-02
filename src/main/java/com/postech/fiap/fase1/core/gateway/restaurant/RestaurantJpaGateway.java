package com.postech.fiap.fase1.core.gateway.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.infrastructure.data.entity.Restaurant;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import com.postech.fiap.fase1.infrastructure.data.mapper.RestaurantMapper;
import com.postech.fiap.fase1.infrastructure.data.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import org.springframework.data.domain.Pageable;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantJpaGateway implements RestaurantGateway {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
   // private final UserGateway userGateway;

    @Override
    public RestaurantDomain create(RestaurantDomain restaurantDomain) {
        final Restaurant restaurant = restaurantMapper.toEntity(restaurantDomain);

        //.setUser(userGateway.getOneUser(restaurant.getUser().getId()));
        return restaurantMapper.toDomain(restaurantRepository.save(restaurant));
    }


    @Override
    public RestaurantDomain update(RestaurantDomain restaurantDomain) {
        var oldRestaurant = findById(restaurantDomain.getId());
        final Restaurant restaurant = restaurantMapper.updateToEntity(restaurantDomain, oldRestaurant);

        //restaurant.setUser(userGateway.getOneUser(restaurant.getUser().getId()));
        return restaurantMapper.toDomain(restaurantRepository.save(restaurant));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasRestaurantWithCNPJ(Long idRestaurant, String cnpj) {
        return restaurantRepository.hasRestaurantWithCNPJ(idRestaurant, cnpj);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RestaurantDomain> getById(Long idRestaurent) {
        return restaurantRepository
                .findById(idRestaurent)
                .map(restaurantMapper::toDomain)
                .or(Optional::empty);
    }

    private Restaurant findById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(
                () -> new ApplicationException("Restaurant Not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RestaurantDomain> getAllPaged(Pageable pageable) {
        return restaurantRepository.findAll(pageable).
                map(restaurantMapper::toDomain);
    }

    @Override
    public void deleteById(Long idRestaurant) {
        restaurantRepository.deleteById(idRestaurant);
    }
}
