package com.postech.fiap.fase1.infrastructure.controller.user;

import com.postech.fiap.fase1.core.controllers.user.UserUpdateCoreController;
import com.postech.fiap.fase1.core.dto.user.UserRequestUpdateDetailsDTO;
import com.postech.fiap.fase1.core.dto.user.UserRequestUpdatePasswordDTO;
import com.postech.fiap.fase1.core.dto.user.UserResponseDTO;
import com.postech.fiap.fase1.infrastructure.data.DataRepository;
import com.postech.fiap.fase1.infrastructure.session.SessionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserUpdateController implements UserControllerInterface {
    private static final String USER_IS_NOT_THE_SAME_AS_THE_ONE_IN_THE_REQUEST = "User is not the same as the one in the request";

    private final DataRepository dataRepository;
    private final SessionRepository sessionRepository;

    @PreAuthorize("hasRole('CLIENT')")
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUserDetails(@PathVariable(value = "id") Long idUser, @Valid @RequestBody UserRequestUpdateDetailsDTO userRequestUpdateDetailsDTO) {
        var userUpdateCoreController = new UserUpdateCoreController(dataRepository, sessionRepository);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdateCoreController.updateUserDetails(idUser, userRequestUpdateDetailsDTO));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PatchMapping("/change-password/{id}")
    public ResponseEntity<UserResponseDTO> updateUserPassword(@PathVariable("id") Long idUser, @Valid @RequestBody UserRequestUpdatePasswordDTO userRequestUpdatePasswordDTO) {
        var userUpdateCoreController = new UserUpdateCoreController(dataRepository, sessionRepository);
        return ResponseEntity.status(HttpStatus.CREATED).body(userUpdateCoreController.updateUserPassword(idUser, userRequestUpdatePasswordDTO));
    }

}
