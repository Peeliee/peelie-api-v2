package kr.higu.peelie.common.auth;

import kr.higu.peelie.common.exception.InvalidParamException;

public class UserContextHolder {
    private static final ThreadLocal<String> userContext = new ThreadLocal<>();

    public static void setUserContext(String userPublicId) {
        userContext.set(userPublicId);
    }

    public static String getUserContext() {
        return userContext.get();
    }

    public static String requireUserContext() {
        String userPublicId = getUserContext();
        if (userPublicId == null || userPublicId.isBlank()) {
            throw new InvalidParamException("user context not found");
        }
        return userPublicId;
    }

    public static void clear() {
        userContext.remove();
    }
}
