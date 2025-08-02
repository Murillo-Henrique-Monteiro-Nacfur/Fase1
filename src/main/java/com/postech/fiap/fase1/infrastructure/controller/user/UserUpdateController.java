package com.postech.fiap.fase1.infrastructure.controller.user;

import com.postech.fiap.fase1.core.presenter.UserPresenter;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import com.postech.fiap.fase1.core.dto.user.UserRequestUpdateDetailsDTO;
import com.postech.fiap.fase1.core.dto.user.UserRequestUpdatePasswordDTO;
import com.postech.fiap.fase1.core.domain.usecase.user.UserUpdateDetailsUseCase;
import com.postech.fiap.fase1.core.domain.usecase.user.UserUpdatePasswordUseCase;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
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

    private final UserUpdateDetailsUseCase userUpdateDetailsUseCase;
    private final UserUpdatePasswordUseCase userUpdatePasswordUseCase;
    private final UserPresenter userPresenter;

    @PreAuthorize("hasRole('CLIENT')")
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserDetails(@PathVariable(value = "id") Long idUser, @Valid @RequestBody UserRequestUpdateDetailsDTO userRequestUpdateDetailsDTO) {
        if (idUser.equals(userRequestUpdateDetailsDTO.getId())) {
            return ResponseEntity.status(HttpStatus.OK).body(userPresenter.toDTO(userUpdateDetailsUseCase.execute(userPresenter.requestUpdateDetailsToInput(userRequestUpdateDetailsDTO))));
        }
        throw new ApplicationException(USER_IS_NOT_THE_SAME_AS_THE_ONE_IN_THE_REQUEST, HttpStatus.BAD_REQUEST);

    }

    @PreAuthorize("hasRole('CLIENT')")
    @PatchMapping("/change-password/{id}")
    public ResponseEntity<UserDTO> updateUserPassword(@PathVariable("id") Long idUser, @Valid @RequestBody UserRequestUpdatePasswordDTO userRequestUpdatePasswordDTO) {
        if (idUser.equals(userRequestUpdatePasswordDTO.getId())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userPresenter.toDTO(userUpdatePasswordUseCase.execute(userPresenter.requestUpdatePasswordToInput(userRequestUpdatePasswordDTO))));
        }
        throw new ApplicationException(USER_IS_NOT_THE_SAME_AS_THE_ONE_IN_THE_REQUEST, HttpStatus.BAD_REQUEST);
    }

}
