package com.postech.fiap.fase1.infrastructure.controller.address;

import com.postech.fiap.fase1.core.presenter.AddressPresenter;
import com.postech.fiap.fase1.core.dto.address.AddressResponseDTO;
import com.postech.fiap.fase1.core.dto.address.AddressRequestDTO;
import com.postech.fiap.fase1.core.domain.usecase.address.AddressUserCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressCreateController implements AddressControllerInterface {

    private final AddressUserCreateUseCase addressUserCreateUseCase;
    private final AddressUserCreateUseCase addressRestaurantCreateUseCase;
    private final AddressPresenter addressPresenter;

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping(path = "/user/{idUser}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddressResponseDTO> createUserAddress(@RequestBody AddressRequestDTO addressRequestDTO, @PathVariable("idUser") Long idUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressPresenter.toDTO(addressUserCreateUseCase.execute(addressPresenter.requestToAddressUserDomain(addressRequestDTO, idUser))));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping(path = "/restaurant/{idRestaurant}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddressResponseDTO> createRestaurantAddress(@RequestBody AddressRequestDTO addressRequestDTO, @PathVariable("idRestaurant") Long idRestaurant) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressPresenter.toDTO(addressRestaurantCreateUseCase.execute(addressPresenter.requestToAddressRestaurantDomain(addressRequestDTO, idRestaurant))));
    }
}