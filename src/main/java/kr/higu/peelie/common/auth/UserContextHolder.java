package kr.higu.peelie.common.auth;

import kr.higu.peelie.common.exception.InvalidParamException;

public class UserContextHolder {
    private static final ThreadLocal<String> userContext = new ThreadLocal<>();

    public static void setUserContext(String userToken) {
        userContext.set(userToken);
    }

    public static String getUserContext() {
        return userContext.get();
    }

    public static String requireUserContext() {
        String userToken = getUserContext();
        if (userToken == null || userToken.isBlank()) {
            throw new InvalidParamException("user context not found");
        }
        return userToken;
    }

    public static void clear() {
        userContext.remove();
    }
}
