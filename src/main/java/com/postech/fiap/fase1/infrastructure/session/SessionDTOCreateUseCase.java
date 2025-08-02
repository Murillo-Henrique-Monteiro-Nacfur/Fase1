package com.postech.fiap.fase1.infrastructure.session;

import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import org.springframework.stereotype.Component;

@Component
public class SessionDTOCreateUseCase {

    public SessionDTO getSessionDTO() {
        return ThreadLocalStorage.getSession();
    }
}
