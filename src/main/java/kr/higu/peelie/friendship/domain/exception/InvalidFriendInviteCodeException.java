package kr.higu.peelie.friendship.domain.exception;

import kr.higu.peelie.common.exception.InvalidParamException;

public class InvalidFriendInviteCodeException extends InvalidParamException {
    public InvalidFriendInviteCodeException() {
        super("만료되거나 유효하지 않은 코드입니다.");
    }
}
