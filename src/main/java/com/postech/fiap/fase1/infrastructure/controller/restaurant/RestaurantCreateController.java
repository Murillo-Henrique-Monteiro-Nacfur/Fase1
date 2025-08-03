package com.postech.fiap.fase1.infrastructure.controller.restaurant;

import com.postech.fiap.fase1.core.controllers.restaurant.RestaurantCreateCoreController;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantRequestDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantResponseDTO;
import com.postech.fiap.fase1.infrastructure.data.DataRepository;
import com.postech.fiap.fase1.infrastructure.session.SessionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantCreateController implements RestaurantControllerInterface {

    private final DataRepository dataRepository;
    private final SessionRepository sessionRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<RestaurantResponseDTO> create(@Valid @RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        RestaurantCreateCoreController restaurantCreateCoreController = new RestaurantCreateCoreController(dataRepository, sessionRepository);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantCreateCoreController.create(restaurantRequestDTO));
    }

}
