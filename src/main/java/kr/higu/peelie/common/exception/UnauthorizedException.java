package kr.higu.peelie.common.exception;

import kr.higu.peelie.common.response.ErrorCode;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException() {
        super(ErrorCode.COMMON_UNAUTHORIZED);
    }

    public UnauthorizedException(String message) {
        super(message, ErrorCode.COMMON_UNAUTHORIZED);
    }
}
