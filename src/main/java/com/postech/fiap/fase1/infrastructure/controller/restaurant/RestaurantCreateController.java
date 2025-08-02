package com.postech.fiap.fase1.infrastructure.controller.restaurant;

import com.postech.fiap.fase1.core.presenter.RestaurantPresenter;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantRequestDTO;
import com.postech.fiap.fase1.core.domain.usecase.restaurant.RestaurantCreateUseCase;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantCreateController implements RestaurantControllerInterface {

    private final RestaurantPresenter restaurantPresenter;
    private final RestaurantCreateUseCase restaurantCreateUseCase;

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<RestaurantDTO> create(@RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        RestaurantDomain restaurantDomain = restaurantCreateUseCase.execute(restaurantPresenter.toModel(restaurantRequestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantPresenter.toDTO(restaurantDomain));
    }

}
