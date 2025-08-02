package com.postech.fiap.fase1.infrastructure.controller.restaurant;

import com.postech.fiap.fase1.core.presenter.RestaurantPresenter;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.domain.usecase.restaurant.RestaurantReadUseCase;
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
    private final RestaurantReadUseCase restaurantReadUseCase;
    private final RestaurantPresenter restaurantPresenter;

    @Operation(
            summary = "Buscar um Restaurante por ID",
            description = "Retorna os dados de um restaurante específico com base no seu ID."
    )
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<RestaurantDTO> findById(@PathVariable("id") Long idRestaurant) {
        var restaurantDomain = restaurantReadUseCase.getById(idRestaurant);
        return ResponseEntity.ok().body(restaurantPresenter.toDTO(restaurantDomain));
    }


    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<RestaurantDTO>> getAllPaged(Pageable pageable) {
        var restaurantDomains = restaurantReadUseCase.getAllPaged(pageable);
        return ResponseEntity.ok().body(restaurantPresenter.toPageDTOs(restaurantDomains));
    }
}