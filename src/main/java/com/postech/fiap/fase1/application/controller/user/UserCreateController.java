package com.postech.fiap.fase1.application.controller.user;


import com.postech.fiap.fase1.application.assembler.UserAssembler;
import com.postech.fiap.fase1.application.dto.UserDTO;
import com.postech.fiap.fase1.application.dto.UserRequestDTO;
import com.postech.fiap.fase1.domain.service.UserService;
import com.postech.fiap.fase1.infrastructure.persistence.entity.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.postech.fiap.fase1.application.disassembler.UserDisassembler.toDTO;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserCreateController implements UserControllerInterface {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<UserDTO> createClient(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return null;//todo ResponseEntity.status(HttpStatus.CREATED).body(toDTO(userService.create(UserAssembler.requestToInput(userRequestDTO, Role.CLIENT))));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<UserDTO> createAdmin(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return null;//todo ResponseEntity.status(HttpStatus.CREATED).body(toDTO(userService.create(UserAssembler.requestToInput(userRequestDTO, Role.ADMIN))));
    }

}
