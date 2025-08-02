package com.postech.fiap.fase1.infrastructure.controller.user;

import com.postech.fiap.fase1.core.domain.usecase.user.UserDeleteUseCase;
import com.postech.fiap.fase1.infrastructure.session.SessionDTOCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserDeleteController implements UserControllerInterface {

    private final UserDeleteUseCase userDeleteUseCase;
    private final SessionDTOCreateUseCase sessionDTOCreateUseCase;

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long idUser) {
        userDeleteUseCase.execute(idUser);
        return ResponseEntity.ok().build();
    }

}
