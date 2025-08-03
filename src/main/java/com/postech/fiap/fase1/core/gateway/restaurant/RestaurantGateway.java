package com.postech.fiap.fase1.core.gateway.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.presenter.RestaurantPresenter;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Slf4j
public class RestaurantGateway implements IRestaurantGateway {

    private static final String RESTAURANT_NOT_FOUND = "Restaurant Not found";
    private final RestaurantPresenter restaurantPresenter;
    private final DataSource dataSource;

    private RestaurantGateway(DataSource dataSource, RestaurantPresenter restaurantPresenter) {
        this.dataSource = dataSource;
        this.restaurantPresenter = restaurantPresenter;
    }
    public static RestaurantGateway build(DataSource dataSource){
        return new RestaurantGateway(dataSource, new RestaurantPresenter());
    }
    @Override
    public RestaurantDomain create(RestaurantDomain restaurantDomain) {
        final RestaurantDTO restaurant = restaurantPresenter.toDTO(restaurantDomain);
        return restaurantPresenter.toDomain(dataSource.createRestaurant(restaurant));
    }


    @Override
    public RestaurantDomain update(RestaurantDomain restaurantDomain) {
        final RestaurantDTO restaurant = restaurantPresenter.toDTO(restaurantDomain);
        return restaurantPresenter.toDomain(dataSource.updateRestaurant(restaurant));
    }

    @Override
    public boolean hasRestaurantWithCNPJ(Long idRestaurant, String cnpj) {
        return dataSource.hasRestaurantWithCNPJ(idRestaurant, cnpj);
    }

    @Override
    public Optional<RestaurantDomain> getById(Long idRestaurent) {
        return dataSource
                .getRestaurantById(idRestaurent)
                .map(restaurantPresenter::toDomain)
                .or(Optional::empty);
    }

    public RestaurantDomain getOneById(Long id) {
        return dataSource.getRestaurantById(id).map(restaurantPresenter::toDomain).orElseThrow(
                () -> new ApplicationException(RESTAURANT_NOT_FOUND));
    }

    @Override

    public Page<RestaurantDomain> getAllPaged(Pageable pageable) {
        return dataSource.getAllRestaurantPaged(pageable).
                map(restaurantPresenter::toDomain);
    }

    @Override
    public void deleteById(Long idRestaurant) {
        dataSource.deleteRestaurantById(idRestaurant);
    }
}
