package com.postech.fiap.fase1.infrastructure.controller.address;

import com.postech.fiap.fase1.core.domain.usecase.address.AddressDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressDeleteController implements AddressControllerInterface {
    private final AddressDeleteUseCase addressDeleteUseCase;

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        addressDeleteUseCase.execute(id);
        return ResponseEntity.ok().build();
    }
}
