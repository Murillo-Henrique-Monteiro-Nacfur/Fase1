package com.postech.fiap.fase1.infrastructure.persistence.gateway.restaurant;

import com.postech.fiap.fase1.domain.model.RestaurantDomain;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface RestaurantGateway {

    RestaurantDomain createOrUpdate(RestaurantDomain restaurantDomain);

    boolean hasRestaurantWithCNPJ(Long idRestaurent, String cnpj);

    Optional<RestaurantDomain> getById(Long idRestaurent);

    Page<RestaurantDomain> getAllPaged(Pageable pageable);

    void deleteById(Long idRestaurant);
}
