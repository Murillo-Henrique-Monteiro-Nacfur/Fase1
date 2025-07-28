package com.postech.fiap.fase1.infrastructure.persistence.gateway.restaurant;

import com.postech.fiap.fase1.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.infrastructure.persistence.entity.Restaurant;
import com.postech.fiap.fase1.infrastructure.persistence.gateway.user.UserGateway;
import com.postech.fiap.fase1.infrastructure.persistence.mapper.RestaurantMapper;
import com.postech.fiap.fase1.infrastructure.persistence.repository.RestaurantRepository;

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
    private final UserGateway userGateway;

    @Override
    public RestaurantDomain createOrUpdate(RestaurantDomain restaurantDomain) {
        final Restaurant restaurant = restaurantMapper.toEntity(restaurantDomain);
        restaurant.getAddresses().forEach(e -> e.setAddressable(restaurant));
        restaurant.setUser(userGateway.getOne(restaurant.getUser().getId()));
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
