package com.postech.fiap.fase1.webapi.controller.restaurant;

import com.postech.fiap.fase1.core.controllers.restaurant.RestaurantReadCoreController;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantResponseDTO;
import com.postech.fiap.fase1.webapi.data.DataRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantReadController implements RestaurantControllerInterface {
    private final DataRepository dataRepository;

    @Operation(
            summary = "Buscar um Restaurante por ID",
            description = "Retorna os dados de um restaurante específico com base no seu ID."
    )
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<RestaurantResponseDTO> findById(@PathVariable("id") Long idRestaurant) {
        RestaurantReadCoreController restaurantReadCoreController = new RestaurantReadCoreController(dataRepository);
        return ResponseEntity.ok().body(restaurantReadCoreController.findById(idRestaurant));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<RestaurantResponseDTO>> getAllPaged(Pageable pageable) {
        RestaurantReadCoreController restaurantReadCoreController = new RestaurantReadCoreController(dataRepository);
        return ResponseEntity.ok().body(restaurantReadCoreController.getAllPaged(pageable));
    }
}