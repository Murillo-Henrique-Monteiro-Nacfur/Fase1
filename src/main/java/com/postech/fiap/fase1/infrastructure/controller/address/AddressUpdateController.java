package com.postech.fiap.fase1.infrastructure.controller.address;

import com.postech.fiap.fase1.core.presenter.AddressPresenter;
import com.postech.fiap.fase1.core.dto.address.AddressDTO;
import com.postech.fiap.fase1.core.dto.address.AddressRequestDTO;
import com.postech.fiap.fase1.core.domain.usecase.address.AddressUpdateUseCase;
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
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressUpdateController implements AddressControllerInterface {
    private final AddressUpdateUseCase addressUpdateUseCase;
    private final AddressPresenter addressPresenter;

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping
    public ResponseEntity<AddressDTO> update(@RequestBody @Valid AddressRequestDTO addressRequestUpdateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressPresenter.toDTO(addressUpdateUseCase.execute(addressPresenter.requestUpdateToInput(addressRequestUpdateDTO))));
    }

}
