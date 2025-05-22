package com.postech.fiap.fase1.domain.controller;

import com.postech.fiap.fase1.configuration.session.SessionService;
import com.postech.fiap.fase1.domain.dto.AdressDTO;
import com.postech.fiap.fase1.domain.dto.AdressRequestDTO;
import com.postech.fiap.fase1.domain.dto.auth.SessionDTO;
import com.postech.fiap.fase1.domain.service.AdressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.postech.fiap.fase1.domain.assembler.AdressAssembler.requestToInput;
import static com.postech.fiap.fase1.domain.assembler.AdressAssembler.toDTO;

@RestController
@RequestMapping("/api/v1/adress")
@RequiredArgsConstructor
public class AdressController {
    private final AdressService adressService;
    private final SessionService sessionService;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<AdressDTO> findById(@PathVariable("id") Long id) {
        SessionDTO sessionDTO = sessionService.getSessionDTO();
        return ResponseEntity.ok(toDTO(adressService.getOneById(id, sessionDTO)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AdressDTO>> findAll() {
        return ResponseEntity.ok(adressService.findAll());
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<AdressDTO> create(@RequestBody AdressRequestDTO adressRequestDTO) {
        SessionDTO sessionDTO = sessionService.getSessionDTO();
        return ResponseEntity.status(HttpStatus.CREATED).body(adressService.createAdress(requestToInput(adressRequestDTO), sessionDTO));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PatchMapping("/{id}")
    public ResponseEntity<AdressDTO> update(@PathVariable(value = "id") Long id, @RequestBody AdressRequestDTO adressRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(adressService.updateAdress(id, requestToInput(adressRequestDTO))));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        SessionDTO sessionDTO = sessionService.getSessionDTO();
        adressService.delete(id, sessionDTO);
        return ResponseEntity.ok().build();
    }
}
