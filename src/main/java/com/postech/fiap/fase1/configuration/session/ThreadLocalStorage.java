package com.postech.fiap.fase1.configuration.session;

import com.postech.fiap.fase1.domain.dto.auth.UserTokenDTO;

public class ThreadLocalStorage {
    private static final String NO_USER_DETECTED = "No user Detected";
    private static ThreadLocal<UserTokenDTO> userTokenThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> tokenThreadLocal = new ThreadLocal<>();

    private ThreadLocalStorage() {
    }

    public static String getUserName() {
        if (userTokenThreadLocal.get() != null) {
            return userTokenThreadLocal.get().getName();
        }
        return NO_USER_DETECTED;
    }

    public static String getUserLogin() {
        if (userTokenThreadLocal.get() != null) {
            return userTokenThreadLocal.get().getLogin();
        }
        return NO_USER_DETECTED;
    }

    public static String getUserRole() {
        if (userTokenThreadLocal.get() != null) {
            return userTokenThreadLocal.get().getRole();
        }
        return NO_USER_DETECTED;
    }

    public static UserTokenDTO getUserThreadLocal() {
        return userTokenThreadLocal.get();
    }

    public static void setUserThreadLocal(UserTokenDTO userTokenDTO) {
        ThreadLocalStorage.userTokenThreadLocal.set(userTokenDTO);
    }

    public static String getTokenThreadLocal() {
        return tokenThreadLocal.get();
    }

    public static void setTokenThreadLocal(String token) {
        ThreadLocalStorage.tokenThreadLocal.set(token);
    }

    public static void build(UserTokenDTO userTokenDTO, String token) {
        setUserThreadLocal(userTokenDTO);
        setTokenThreadLocal(token);
    }

    public static void clear() {
        userTokenThreadLocal.remove();
        tokenThreadLocal.remove();
    }
}
