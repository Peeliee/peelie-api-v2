package kr.higu.peelie.friendship.domain.exception;

import kr.higu.peelie.common.exception.InvalidParamException;

public class InvalidFriendInviteCodeException extends InvalidParamException {
    public InvalidFriendInviteCodeException() {
        super("friend invite code is invalid");
    }
}
