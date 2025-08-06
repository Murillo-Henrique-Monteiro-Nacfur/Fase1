package com.postech.fiap.fase1.webapi.controller.user;

import com.postech.fiap.fase1.core.controllers.user.UserReadCoreController;
import com.postech.fiap.fase1.core.dto.user.UserResponseDTO;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserReadController implements UserControllerInterface {

    private final DataRepository dataRepository;
    private final SessionRepository sessionRepository;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable("id") Long id) {
        UserReadCoreController userReadCoreController = new UserReadCoreController(dataRepository, sessionRepository);
        return ResponseEntity.ok((userReadCoreController.findById(id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> findAll(Pageable pageable) {
        UserReadCoreController userReadCoreController = new UserReadCoreController(dataRepository, sessionRepository);
        return ResponseEntity.ok(userReadCoreController.findAll(pageable));
    }

}
