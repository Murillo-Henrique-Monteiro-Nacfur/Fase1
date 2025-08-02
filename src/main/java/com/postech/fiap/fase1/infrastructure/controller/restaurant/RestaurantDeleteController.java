package com.postech.fiap.fase1.infrastructure.controller.restaurant;

import com.postech.fiap.fase1.core.domain.usecase.restaurant.RestaurantDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantDeleteController implements RestaurantControllerInterface {

    private final RestaurantDeleteUseCase restaurantDeleteUseCase;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long idRestaurant) {
        restaurantDeleteUseCase.execute(idRestaurant);
        return ResponseEntity.ok().build();
    }

}
