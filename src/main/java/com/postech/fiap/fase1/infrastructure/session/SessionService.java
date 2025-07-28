package com.postech.fiap.fase1.infrastructure.session;

import com.postech.fiap.fase1.application.dto.auth.SessionDTO;
import org.springframework.stereotype.Component;

@Component
public class SessionService {

    public SessionDTO getSessionDTO() {
        return ThreadLocalStorage.getSession();
    }
}
