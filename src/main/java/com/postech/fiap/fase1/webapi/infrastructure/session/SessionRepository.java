package com.postech.fiap.fase1.webapi.infrastructure.session;

import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import org.springframework.stereotype.Repository;

@Repository
public class SessionRepository implements SessionSource {

    public SessionDTO getSessionDTO() {
        return ThreadLocalStorage.getSession();
    }
}
