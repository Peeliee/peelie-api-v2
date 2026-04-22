package kr.higu.peelie.user.domain.exception;

import kr.higu.peelie.common.exception.BaseException;
import kr.higu.peelie.common.response.ErrorCode;

public class OAuthUserProfileException extends BaseException {

    public OAuthUserProfileException(String message) {
        super(message, ErrorCode.COMMON_SYSTEM_ERROR);
    }

    public OAuthUserProfileException(String message, Throwable cause) {
        super(message, ErrorCode.COMMON_SYSTEM_ERROR, cause);
    }
}

