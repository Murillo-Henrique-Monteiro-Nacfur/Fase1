package com.postech.fiap.fase1.core.gateway.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface IRestaurantGateway {

    RestaurantDomain create(RestaurantDomain restaurantDomain);
    RestaurantDomain update(RestaurantDomain restaurantDomain);
    boolean hasRestaurantWithCNPJ(Long idRestaurent, String cnpj);

    Optional<RestaurantDomain> getById(Long idRestaurent);
    RestaurantDomain getOneById(Long idRestaurent);
    Page<RestaurantDomain> getAllPaged(Pageable pageable);

    void deleteById(Long idRestaurant);
}
