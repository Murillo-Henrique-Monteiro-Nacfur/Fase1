package com.postech.fiap.fase1.controller;

import com.postech.fiap.fase1.configuration.session.SessionService;
import com.postech.fiap.fase1.domain.dto.AddressDTO;
import com.postech.fiap.fase1.domain.dto.AddressRequestDTO;
import com.postech.fiap.fase1.domain.dto.AddressRequestUpdateDTO;
import com.postech.fiap.fase1.domain.dto.auth.SessionDTO;
import com.postech.fiap.fase1.domain.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.postech.fiap.fase1.domain.assembler.AddressAssembler.*;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
    private final SessionService sessionService;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable("id") Long id) {
        SessionDTO sessionDTO = sessionService.getSessionDTO();
        return ResponseEntity.ok(toDTO(addressService.getOneById(id, sessionDTO)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAll() {
        return ResponseEntity.ok(addressService.findAll());
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddressDTO> create(@RequestBody AddressRequestDTO addressRequestDTO) {
        SessionDTO sessionDTO = sessionService.getSessionDTO();
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAdress(requestToInput(addressRequestDTO), sessionDTO));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable(value = "id") Long id, @RequestBody @Valid AddressRequestUpdateDTO addressRequestUpdateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(addressService.updateAdress(id, requestUpdateToInput(addressRequestUpdateDTO))));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        SessionDTO sessionDTO = sessionService.getSessionDTO();
        addressService.delete(id, sessionDTO);
        return ResponseEntity.ok().build();
    }
}
