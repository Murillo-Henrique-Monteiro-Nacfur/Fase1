package com.postech.fiap.fase1.application.controller.restaurant;

import com.postech.fiap.fase1.application.usecase.restaurant.RestaurantCreateUseCase;
import com.postech.fiap.fase1.application.assembler.RestaurantAssembler;
import com.postech.fiap.fase1.application.disassembler.RestaurantDisassembler;
import com.postech.fiap.fase1.application.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.application.dto.restaurant.RestaurantRequestDTO;
import com.postech.fiap.fase1.domain.model.RestaurantDomain;
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

    private final RestaurantAssembler restaurantAssembler;
    private final RestaurantDisassembler restaurantDisassembler;
    private final RestaurantCreateUseCase restaurantCreateUseCase;

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<RestaurantDTO> create(@RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        RestaurantDomain restaurantDomain = restaurantCreateUseCase.execute(restaurantAssembler.toModel(restaurantRequestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantDisassembler.toDTO(restaurantDomain));
    }

}
