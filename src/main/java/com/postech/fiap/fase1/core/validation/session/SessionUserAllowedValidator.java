package com.postech.fiap.fase1.core.validation.session;

import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionUserAllowedValidator implements ISessionValidation {
    private static final String USER_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION = "User not authorized to perform this action";
    private final ISessionGateway sessionGateway;

    public SessionUserAllowedValidator(ISessionGateway sessionGateway) {
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
