package com.postech.fiap.fase1.infrastructure.session;

import com.postech.fiap.fase1.application.dto.auth.SessionDTO;
import com.postech.fiap.fase1.application.dto.auth.UserTokenDTO;

public class ThreadLocalStorage {
    private static ThreadLocal<UserTokenDTO> userTokenThreadLocal = new ThreadLocal<>();

    private ThreadLocalStorage() {
    }

    public static SessionDTO getSession() {
        if (userTokenThreadLocal.get() != null) {
            return new SessionDTO(userTokenThreadLocal.get());
        }
        return new SessionDTO();
    }

    public static void build(UserTokenDTO userTokenDTO) {
        ThreadLocalStorage.userTokenThreadLocal.set(userTokenDTO);
    }

    public static void clear() {
        userTokenThreadLocal.remove();
    }
}
