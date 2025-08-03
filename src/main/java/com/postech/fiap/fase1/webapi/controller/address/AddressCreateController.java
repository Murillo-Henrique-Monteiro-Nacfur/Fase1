package com.postech.fiap.fase1.webapi.controller.address;

import com.postech.fiap.fase1.core.controllers.address.AddressCreateCoreController;
import com.postech.fiap.fase1.core.dto.address.AddressRequestDTO;
import com.postech.fiap.fase1.core.dto.address.AddressResponseDTO;
import com.postech.fiap.fase1.webapi.data.DataRepository;
import com.postech.fiap.fase1.webapi.infrastructure.session.SessionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressCreateController implements AddressControllerInterface {

    private final DataRepository dataRepository;
    private final SessionRepository sessionRepository;

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping(path = "/user/{idUser}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddressResponseDTO> createUserAddress(@Valid @RequestBody AddressRequestDTO addressRequestDTO, @PathVariable("idUser") Long idUser) {
        AddressCreateCoreController addressCreateCoreController = new AddressCreateCoreController(dataRepository, sessionRepository);
        return ResponseEntity.status(HttpStatus.CREATED).body(addressCreateCoreController.createUserAddress(addressRequestDTO, idUser));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/restaurant/{idRestaurant}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddressResponseDTO> createRestaurantAddress(@Valid @RequestBody AddressRequestDTO addressRequestDTO, @PathVariable("idRestaurant") Long idRestaurant) {
        AddressCreateCoreController addressCreateCoreController = new AddressCreateCoreController(dataRepository, sessionRepository);
        return ResponseEntity.status(HttpStatus.CREATED).body(addressCreateCoreController.createRestaurantAddress(addressRequestDTO, idRestaurant));
    }
}