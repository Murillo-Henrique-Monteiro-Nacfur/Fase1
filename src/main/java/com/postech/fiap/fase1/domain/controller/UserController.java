package com.postech.fiap.fase1.domain.controller;


import com.postech.fiap.fase1.configuration.exception.ApplicationException;
import com.postech.fiap.fase1.configuration.session.SessionService;
import com.postech.fiap.fase1.domain.assembler.UserAssembler;
import com.postech.fiap.fase1.domain.dto.UserDTO;
import com.postech.fiap.fase1.domain.dto.UserRequestDTO;
import com.postech.fiap.fase1.domain.dto.UserRequestUpdateDetailsDTO;
import com.postech.fiap.fase1.domain.dto.UserRequestUpdatePasswordDTO;
import com.postech.fiap.fase1.domain.dto.auth.SessionDTO;
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

    private static final String USER_IS_NOT_THE_SAME_AS_THE_ONE_IN_THE_REQUEST = "User is not the same as the one in the request";
    private final UserService userService;
    private final SessionService sessionService;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {
        SessionDTO sessionDTO = sessionService.getSessionDTO();
        return ResponseEntity.ok(toDTO(userService.getOne(sessionDTO, id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(toDTO(userService.findAll(pageable)));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createClient(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(userService.create(UserAssembler.requestToInput(userRequestDTO, Role.CLIENT))));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<UserDTO> createAdmin(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(userService.create(UserAssembler.requestToInput(userRequestDTO, Role.ADMIN))));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserDetails(@PathVariable(value = "id") Long idUser, @RequestBody UserRequestUpdateDetailsDTO userRequestUpdateDetailsDTO) {
        if (idUser.equals(userRequestUpdateDetailsDTO.getId())) {
            SessionDTO sessionDTO = sessionService.getSessionDTO();
            return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(userService.updateDetails(sessionDTO, UserAssembler.requestUpdateDetailsToInput(userRequestUpdateDetailsDTO))));
        }
        throw new ApplicationException(USER_IS_NOT_THE_SAME_AS_THE_ONE_IN_THE_REQUEST, HttpStatus.BAD_REQUEST);

    }

    @PreAuthorize("hasRole('CLIENT')")
    @PatchMapping("/change-password/{id}")
    public ResponseEntity<UserDTO> updateUserPassword(@PathVariable("id") Long idUser, @RequestBody UserRequestUpdatePasswordDTO userRequestUpdatePasswordDTO) {
        if (idUser.equals(userRequestUpdatePasswordDTO.getId())) {
            SessionDTO sessionDTO = sessionService.getSessionDTO();
            return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(userService.updatePassword(sessionDTO, UserAssembler.requestUpdatePasswordToInput(userRequestUpdatePasswordDTO))));
        }
        throw new ApplicationException(USER_IS_NOT_THE_SAME_AS_THE_ONE_IN_THE_REQUEST, HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long idUser) {
        SessionDTO sessionDTO = sessionService.getSessionDTO();
        userService.delete(sessionDTO, idUser);
        return ResponseEntity.ok().build();
    }
}
