package com.postech.fiap.fase1.configuration.session;

import com.postech.fiap.fase1.domain.dto.auth.SessionDTO;
import org.springframework.stereotype.Component;

@Component
public class SessionService {

    public SessionDTO getSessionDTO() {
        return ThreadLocalStorage.getSession();
    }
}
