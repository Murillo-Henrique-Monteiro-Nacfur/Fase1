package com.postech.fiap.fase1.infrastructure.controller.restaurant;


import com.postech.fiap.fase1.core.presenter.RestaurantPresenter;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantRequestDTO;
import com.postech.fiap.fase1.core.domain.usecase.restaurant.RestaurantUpdateUseCase;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantUpdateController implements RestaurantControllerInterface {

    private final RestaurantPresenter restaurantPresenter;
    private final RestaurantUpdateUseCase restaurantUpdateUseCase;

    @PutMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<RestaurantDTO> update(@RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        RestaurantDomain restaurantDomain = restaurantUpdateUseCase.execute(restaurantPresenter.toModel(restaurantRequestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantPresenter.toDTO(restaurantDomain));
    }


}
