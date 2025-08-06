package com.postech.fiap.fase1.webapi.controller.restaurant;

import com.postech.fiap.fase1.core.controllers.restaurant.RestaurantUpdateCoreController;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantRequestUpdateDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantResponseDTO;
import com.postech.fiap.fase1.webapi.data.DataRepository;
import com.postech.fiap.fase1.webapi.infrastructure.session.SessionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantUpdateController implements RestaurantControllerInterface {

    private final DataRepository dataRepository;
    private final SessionRepository sessionRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<RestaurantResponseDTO> update(@Valid @RequestBody RestaurantRequestUpdateDTO restaurantRequestUpdateDTO) {
        RestaurantUpdateCoreController restaurantUpdateCoreController = new RestaurantUpdateCoreController(dataRepository, sessionRepository);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantUpdateCoreController.update(restaurantRequestUpdateDTO));
    }
}
