package com.postech.fiap.fase1.infrastructure.controller.user;

import com.postech.fiap.fase1.core.controllers.user.UserDeleteCoreController;
import com.postech.fiap.fase1.infrastructure.data.DataRepository;
import com.postech.fiap.fase1.infrastructure.session.SessionRepository;
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

    private final DataRepository dataRepository;
    private final SessionRepository sessionRepository;

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long idUser) {
        var userDeleteCoreController = new UserDeleteCoreController(dataRepository, sessionRepository);
        userDeleteCoreController.delete(idUser);
        return ResponseEntity.ok().build();
    }

}
