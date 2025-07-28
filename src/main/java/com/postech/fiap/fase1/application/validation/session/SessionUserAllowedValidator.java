package com.postech.fiap.fase1.application.validation.session;

import com.postech.fiap.fase1.application.dto.auth.SessionDTO;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.infrastructure.session.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SessionUserAllowedValidator implements SessionValidation {
    private static final String USER_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION = "User not authorized to perform this action";
    private final SessionService sessionService;

    public void validate(Long idUser) {

        SessionDTO sessionDTO = sessionService.getSessionDTO();
        if (sessionDTO.isNotAdmin() && !sessionDTO.getUserId().equals(idUser)) {
            log.warn(USER_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION);
            throw new ApplicationException(USER_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION);
        }
    }

    public void validate() {
        validate(null);
    }
}
