package com.postech.fiap.fase1.webapi.infrastructure.session;

import com.postech.fiap.fase1.core.dto.auth.SessionDTO;
import com.postech.fiap.fase1.core.dto.auth.UserTokenBodyDTO;

public class ThreadLocalStorage {
    private static ThreadLocal<UserTokenBodyDTO> userTokenThreadLocal = new ThreadLocal<>();

    private ThreadLocalStorage() {
    }

    public static SessionDTO getSession() {
        if (userTokenThreadLocal.get() != null) {
            return new SessionDTO(userTokenThreadLocal.get());
        }
        return new SessionDTO();
    }

    public static void build(UserTokenBodyDTO userTokenBodyDTO) {
        ThreadLocalStorage.userTokenThreadLocal.set(userTokenBodyDTO);
    }

    public static void clear() {
        userTokenThreadLocal.remove();
    }
}
