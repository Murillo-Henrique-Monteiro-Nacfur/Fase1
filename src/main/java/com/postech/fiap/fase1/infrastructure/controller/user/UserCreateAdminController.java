package com.postech.fiap.fase1.infrastructure.controller.user;

import com.postech.fiap.fase1.core.presenter.UserPresenter;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import com.postech.fiap.fase1.core.dto.user.UserRequestDTO;
import com.postech.fiap.fase1.core.domain.usecase.user.UserCreateAdminUseCase;
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
    private final UserCreateAdminUseCase userCreateAdminUseCase;
    private final UserPresenter userPresenter;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<UserDTO> createAdmin(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userPresenter.toDTO(userCreateAdminUseCase.execute(userPresenter.requestToDomain(userRequestDTO))));
    }
}