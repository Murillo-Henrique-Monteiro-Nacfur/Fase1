package com.postech.fiap.fase1.domain.controller;


import com.postech.fiap.fase1.domain.assembler.UserAssembler;
import com.postech.fiap.fase1.domain.dto.UserDTO;
import com.postech.fiap.fase1.domain.dto.UserRequestDTO;
import com.postech.fiap.fase1.domain.model.Role;
import com.postech.fiap.fase1.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.postech.fiap.fase1.domain.assembler.UserDisassembler.toDTO;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PreAuthorize("hasRole('VIEWER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(toDTO(userService.getOne(id)));
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(toDTO(userService.findAll(pageable)));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createClient(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(userService.create(UserAssembler.requestToInput(userRequestDTO, Role.CLIENT))));
    }

    @PostMapping("/admin")
    public ResponseEntity<UserDTO> createAdmin(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(userService.create(UserAssembler.requestToInput(userRequestDTO, Role.ADMIN))));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserInformations(@PathVariable("id") Long idUser, @RequestBody UserRequestDTO userRequestDTO) {//todo
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(userService.create(UserAssembler.requestToInput(userRequestDTO, Role.ADMIN))));
    }

    @PatchMapping("/change-password/{id}")
    public ResponseEntity<UserDTO> updateuserPassword(@PathVariable("id") Long idUser, @RequestBody UserRequestDTO userRequestDTO) {//todo
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(userService.create(UserAssembler.requestToInput(userRequestDTO, Role.ADMIN))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long idUser) {
        userService.delete(idUser);
        return ResponseEntity.ok().build();
    }
}
