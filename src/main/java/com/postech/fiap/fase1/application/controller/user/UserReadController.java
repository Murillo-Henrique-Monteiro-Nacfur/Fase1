package com.postech.fiap.fase1.application.controller.user;

import com.postech.fiap.fase1.application.disassembler.UserDisassembler2;
import com.postech.fiap.fase1.application.dto.UserDTO;
import com.postech.fiap.fase1.application.usecase.user.UserReadUseCase;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserReadController implements UserControllerInterface {
    private final UserReadUseCase userReadUseCase;
    private final UserDisassembler2 userDisassembler;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userDisassembler.toDTO(userReadUseCase.getById(id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(userDisassembler.toDTO(userReadUseCase.getAllPaged(pageable)));
    }

}
