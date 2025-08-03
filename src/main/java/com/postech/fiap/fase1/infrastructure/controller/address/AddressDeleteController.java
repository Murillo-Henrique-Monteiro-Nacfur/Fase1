package com.postech.fiap.fase1.infrastructure.controller.address;

import com.postech.fiap.fase1.core.controllers.address.AddressDeleteCoreController;
import com.postech.fiap.fase1.infrastructure.data.DataRepository;
import com.postech.fiap.fase1.infrastructure.session.SessionRepository;
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

    private final DataRepository dataRepository;
    private final SessionRepository sessionRepository;

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        AddressDeleteCoreController addressDeleteCoreController = new AddressDeleteCoreController(dataRepository, sessionRepository);
        addressDeleteCoreController.delete(id);
        return ResponseEntity.ok().build();
    }
}
