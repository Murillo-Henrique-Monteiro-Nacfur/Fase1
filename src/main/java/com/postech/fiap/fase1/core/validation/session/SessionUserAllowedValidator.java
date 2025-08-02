package com.postech.fiap.fase1.core.validation.session;

import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionUserAllowedValidator implements SessionValidation {
    private static final String USER_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION = "User not authorized to perform this action";
    private final SessionGateway sessionGateway;

    public SessionUserAllowedValidator(SessionGateway sessionGateway) {
        this.sessionGateway = sessionGateway;
    }

    public void validate(Long idUser) {
        SessionDTO sessionDTO = sessionGateway.getSessionDTO();
        if (sessionDTO.isNotAdmin() && !sessionDTO.getUserId().equals(idUser)) {
            log.warn(USER_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION);
            throw new ApplicationException(USER_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION);
        }
    }

    public void validate() {
        validate(null);
    }
}
