package com.postech.fiap.fase1.webapi.controller.user;


import com.postech.fiap.fase1.core.controllers.user.UserCreateCoreController;
import com.postech.fiap.fase1.core.dto.user.UserRequestDTO;
import com.postech.fiap.fase1.core.dto.user.UserResponseDTO;
import com.postech.fiap.fase1.webapi.data.DataRepository;
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

    private final DataRepository dataRepository;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createClient(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserCreateCoreController userCreateCoreController = new UserCreateCoreController(dataRepository);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreateCoreController.createClient(userRequestDTO));
    }
}
