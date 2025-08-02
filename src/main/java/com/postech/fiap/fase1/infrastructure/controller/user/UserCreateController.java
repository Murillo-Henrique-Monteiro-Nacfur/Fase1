package com.postech.fiap.fase1.infrastructure.controller.user;


import com.postech.fiap.fase1.core.presenter.UserPresenter;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import com.postech.fiap.fase1.core.dto.user.UserRequestDTO;
import com.postech.fiap.fase1.core.domain.usecase.user.UserCreateUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserCreateController implements UserControllerInterface {
    private final UserCreateUseCase userCreateUseCase;
    private final UserPresenter userPresenter;

    @PostMapping
    public ResponseEntity<UserDTO> createClient(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userPresenter.toDTO(userCreateUseCase.execute(userPresenter.requestToDomain(userRequestDTO))));
    }
}
