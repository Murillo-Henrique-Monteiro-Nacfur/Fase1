package com.postech.fiap.fase1.application.controller.user;

import com.postech.fiap.fase1.application.assembler.UserAssembler;
import com.postech.fiap.fase1.application.dto.UserDTO;
import com.postech.fiap.fase1.application.dto.UserRequestUpdateDetailsDTO;
import com.postech.fiap.fase1.application.dto.UserRequestUpdatePasswordDTO;
import com.postech.fiap.fase1.application.dto.auth.SessionDTO;
import com.postech.fiap.fase1.domain.service.UserService;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.infrastructure.session.SessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.postech.fiap.fase1.application.disassembler.UserDisassembler.toDTO;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserUpdateController implements UserControllerInterface {
    private static final String USER_IS_NOT_THE_SAME_AS_THE_ONE_IN_THE_REQUEST = "User is not the same as the one in the request";
    private final UserService userService;
    private final SessionService sessionService;

    @PreAuthorize("hasRole('CLIENT')")
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserDetails(@PathVariable(value = "id") Long idUser, @Valid @RequestBody UserRequestUpdateDetailsDTO userRequestUpdateDetailsDTO) {
        if (idUser.equals(userRequestUpdateDetailsDTO.getId())) {
            SessionDTO sessionDTO = sessionService.getSessionDTO();
            return null;//todo ResponseEntity.status(HttpStatus.CREATED).body(toDTO(userService.updateDetails(sessionDTO, UserAssembler.requestUpdateDetailsToInput(userRequestUpdateDetailsDTO))));
        }
        throw new ApplicationException(USER_IS_NOT_THE_SAME_AS_THE_ONE_IN_THE_REQUEST, HttpStatus.BAD_REQUEST);

    }

    @PreAuthorize("hasRole('CLIENT')")
    @PatchMapping("/change-password/{id}")
    public ResponseEntity<UserDTO> updateUserPassword(@PathVariable("id") Long idUser, @Valid @RequestBody UserRequestUpdatePasswordDTO userRequestUpdatePasswordDTO) {
        if (idUser.equals(userRequestUpdatePasswordDTO.getId())) {
            SessionDTO sessionDTO = sessionService.getSessionDTO();
            return null;//todo ResponseEntity.status(HttpStatus.CREATED).body(toDTO(userService.updatePassword(sessionDTO, UserAssembler.requestUpdatePasswordToInput(userRequestUpdatePasswordDTO))));
        }
        throw new ApplicationException(USER_IS_NOT_THE_SAME_AS_THE_ONE_IN_THE_REQUEST, HttpStatus.BAD_REQUEST);
    }

}
