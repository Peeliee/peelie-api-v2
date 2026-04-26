package kr.higu.peelie.friendship.domain.exception;

import kr.higu.peelie.common.exception.InvalidParamException;

public class AlreadyFriendException extends InvalidParamException {
    public AlreadyFriendException() {
        super("already friend");
    }
}
