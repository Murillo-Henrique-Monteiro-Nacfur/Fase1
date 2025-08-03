package com.postech.fiap.fase1.core.gateway.session;

import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.gateway.SessionSource;


public class SessionGateway implements ISessionGateway {

    private final SessionSource sessionSource;

    private SessionGateway(SessionSource sessionSource) {
        this.sessionSource = sessionSource;
    }
    public static SessionGateway build(SessionSource sessionSource) {
        return new SessionGateway(sessionSource);
    }

    @Override
    public SessionDTO getSessionDTO() {
        return sessionSource.getSessionDTO();
    }
}
