package com.postech.fiap.fase1.webapi.controller.address;

import com.postech.fiap.fase1.core.controllers.address.AddressReadCoreController;
import com.postech.fiap.fase1.core.dto.address.AddressResponseDTO;
import com.postech.fiap.fase1.webapi.data.DataRepository;
import com.postech.fiap.fase1.webapi.infrastructure.session.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressReadController implements AddressControllerInterface {

    private final DataRepository dataRepository;
    private final SessionRepository sessionRepository;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> findById(@PathVariable("id") Long id) {
        AddressReadCoreController addressReadCoreController = new AddressReadCoreController(dataRepository, sessionRepository);
        return ResponseEntity.ok(addressReadCoreController.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<AddressResponseDTO>> findAllPaged(Pageable pageable) {
        AddressReadCoreController addressReadCoreController = new AddressReadCoreController(dataRepository, sessionRepository);
        return ResponseEntity.ok(addressReadCoreController.findAllPaged(pageable));
    }
}
