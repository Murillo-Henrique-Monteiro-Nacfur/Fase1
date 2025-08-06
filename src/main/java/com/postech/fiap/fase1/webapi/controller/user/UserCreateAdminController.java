package com.postech.fiap.fase1.webapi.controller.user;

import com.postech.fiap.fase1.core.controllers.user.UserCreateAdminCoreController;
import com.postech.fiap.fase1.core.dto.user.UserRequestDTO;
import com.postech.fiap.fase1.core.dto.user.UserResponseDTO;
import com.postech.fiap.fase1.webapi.data.DataRepository;
import com.postech.fiap.fase1.webapi.infrastructure.session.SessionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserCreateAdminController implements UserControllerInterface {

    private final DataRepository dataRepository;
    private final SessionRepository sessionRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<UserResponseDTO> createAdmin(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserCreateAdminCoreController userCreateAdminCoreController = new UserCreateAdminCoreController(dataRepository, sessionRepository);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreateAdminCoreController.createAdmin(userRequestDTO));
    }
}