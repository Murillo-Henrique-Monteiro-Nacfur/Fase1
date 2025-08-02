package com.postech.fiap.fase1.infrastructure.controller.address;

import com.postech.fiap.fase1.core.presenter.AddressPresenter;
import com.postech.fiap.fase1.core.dto.address.AddressResponseDTO;
import com.postech.fiap.fase1.core.domain.usecase.address.AddressReadUseCase;
import com.postech.fiap.fase1.core.domain.usecase.address.AddressUserCreateUseCase;
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
    private final AddressUserCreateUseCase addressUserCreateUseCase;
    private final AddressReadUseCase addressReadUseCase;
    private final AddressPresenter addressPresenter;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(addressPresenter.toDTO(addressReadUseCase.getById(id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<AddressResponseDTO>> findAllPaged(Pageable pageable) {
        return ResponseEntity.ok(addressPresenter.toDTO(addressReadUseCase.getAllPaged(pageable)));
    }

}
