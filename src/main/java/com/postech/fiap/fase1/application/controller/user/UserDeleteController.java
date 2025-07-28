package com.postech.fiap.fase1.application.controller.user;

import com.postech.fiap.fase1.application.dto.auth.SessionDTO;
import com.postech.fiap.fase1.domain.service.UserService;
import com.postech.fiap.fase1.infrastructure.session.SessionService;
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
    private final UserService userService;
    private final SessionService sessionService;
    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long idUser) {
        SessionDTO sessionDTO = sessionService.getSessionDTO();
        userService.delete(sessionDTO, idUser);
        return ResponseEntity.ok().build();
    }

}
