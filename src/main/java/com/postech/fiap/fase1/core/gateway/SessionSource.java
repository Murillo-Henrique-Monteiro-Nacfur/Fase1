package com.postech.fiap.fase1.core.gateway;

import com.postech.fiap.fase1.core.dto.auth.SessionDTO;

public interface SessionSource {
    SessionDTO getSessionDTO();
}
