package com.postech.fiap.fase1.infrastructure.controller.restaurant;

import com.postech.fiap.fase1.core.controllers.restaurant.RestaurantDeleteCoreController;
import com.postech.fiap.fase1.infrastructure.data.DataRepository;
import com.postech.fiap.fase1.infrastructure.session.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantDeleteController implements RestaurantControllerInterface {

    private final DataRepository dataRepository;
    private final SessionRepository sessionRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long idRestaurant) {
        RestaurantDeleteCoreController restaurantDeleteCoreController = new RestaurantDeleteCoreController(dataRepository, sessionRepository);
        restaurantDeleteCoreController.delete(idRestaurant);
        return ResponseEntity.ok().build();
    }

}
