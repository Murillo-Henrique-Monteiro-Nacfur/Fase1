package com.postech.fiap.fase1.core.gateway.session;

import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.gateway.SessionSource;


public class SessionGateway implements ISessionGateway {

    private final SessionSource sessionSource;

    public SessionGateway(SessionSource sessionSource) {
        this.sessionSource = sessionSource;
    }

    @Override
    public SessionDTO getSessionDTO() {
        return sessionSource.getSessionDTO();
    }
}
