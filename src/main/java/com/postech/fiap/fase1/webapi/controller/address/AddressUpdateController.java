package com.postech.fiap.fase1.webapi.controller.address;

import com.postech.fiap.fase1.core.controllers.address.AddressUpdateCoreController;
import com.postech.fiap.fase1.core.dto.address.AddressRequestDTO;
import com.postech.fiap.fase1.core.dto.address.AddressResponseDTO;
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
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressUpdateController implements AddressControllerInterface {

    private final DataRepository dataRepository;
    private final SessionRepository sessionRepository;

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping
    public ResponseEntity<AddressResponseDTO> update(@RequestBody @Valid AddressRequestDTO addressRequestUpdateDTO) {
        AddressUpdateCoreController addressUpdateCoreController = new AddressUpdateCoreController(dataRepository, sessionRepository);
        return ResponseEntity.status(HttpStatus.CREATED).body(addressUpdateCoreController.update(addressRequestUpdateDTO));
    }

}
