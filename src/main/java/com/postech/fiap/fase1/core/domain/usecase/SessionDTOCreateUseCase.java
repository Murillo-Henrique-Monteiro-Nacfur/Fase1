package com.postech.fiap.fase1.core.domain.usecase;

import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;

public class SessionDTOCreateUseCase {

    private final ISessionGateway iSessionGateway;

    public SessionDTOCreateUseCase(ISessionGateway iSessionGateway) {
        this.iSessionGateway = iSessionGateway;
    }

    public SessionDTO getSessionDTO() {
        return iSessionGateway.getSessionDTO();
    }
}
