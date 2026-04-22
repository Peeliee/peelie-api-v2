package kr.higu.peelie.user.domain.exception;

import kr.higu.peelie.common.exception.BaseException;
import kr.higu.peelie.common.response.ErrorCode;

public class OAuthTokenException extends BaseException {

    public OAuthTokenException(String message) {
        super(message, ErrorCode.COMMON_SYSTEM_ERROR);
    }

    public OAuthTokenException(String message, Throwable cause) {
        super(message, ErrorCode.COMMON_SYSTEM_ERROR, cause);
    }
}
